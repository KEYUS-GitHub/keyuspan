package org.keyus.project.keyuspan.file.provider.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.file.provider.dao.FileModelDao;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author keyus
 * @create 2019-07-22  下午10:05
 */
@Service
@AllArgsConstructor
public class FileModelServiceImpl implements FileModelService, ITxTransaction {

    private final FileModelDao fileModelDao;

    private final MemberClientService memberClientService;

    @Resource(name = "fileProviderExecutor")
    private ThreadPoolExecutor executor;

    @Override
    public Optional<FileModel> findById (Long id) {
        return fileModelDao.findById(id);
    }

    @Override
    public List<FileModel> findByIdIn (Iterable<Long> iterable) {
        return fileModelDao.findByIdIn(iterable);
    }

    @TxTransaction
    @Transactional
    @Override
    public FileModel saveFile (FileModel fileModel) {
        return fileModelDao.save(fileModel);
    }

    @TxTransaction
    @Transactional
    @Override
    public List<FileModel> saveFiles (List<FileModel> list) {
        if (Objects.isNull(list)) {
            return Collections.emptyList();
        }
        // 执行更新操作
        return fileModelDao.saveAll(list);
    }

    @Override
    public List<FileModel> findAll (FileModel fileModel) {
        return fileModelDao.findAll(Example.of(fileModel));
    }

    @Override
    public List<FileModel> getFilesByFolderId(Long id) {
        FileModel fileModel = FileModel.builder()
                .id(id).deleted(false).build();
        return fileModelDao.findAll(Example.of(fileModel));
    }

    @TxTransaction
    @Transactional
    @Override
    public List<FileModel> deleteFilesInRecycleBin (Long memberId , HttpSession session) throws ExecutionException, InterruptedException {
        Member member = memberClientService.findOne(Member.builder().id(memberId).build());
        if (Objects.isNull(member.getId())) {
            final List<FileModel> result = new ArrayList<>();
            Future<Double> future = executor.submit(() -> {
                FileModel fileModel = FileModel.builder().memberId(memberId).deleted(true)
                        .dateOfRecovery(LocalDate.now()).build();
                // 根据会员ID查询该会员需要删除的文件模型记录
                List<FileModel> all = fileModelDao.findAll(Example.of(fileModel));
                double size = 0.0;
                for (FileModel fm : all) {
                    size += fm.getSize();
                }
                result.addAll(all);
                // 执行删除
                executor.execute(() -> fileModelDao.deleteInBatch(all));
                return size;
            });

            // 修改member的已经使用的空间
            double size = member.getUsedStorageSpace() - future.get();
            member.setUsedStorageSpace(size);
            Member save = memberClientService.saveMember(member);
            session.setAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName(), save);
            return result;
        } else {
            return Collections.emptyList();
        }
    }
}
