package org.keyus.project.keyuspan.file.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
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
@Transactional
@AllArgsConstructor
public class FileModelServiceImpl implements FileModelService {

    private final FileModelDao fileModelDao;

    private final MemberClientService memberClientService;

    @Resource(name = "fileProviderExecutor")
    private ThreadPoolExecutor executor;

    @Override
    public ServerResponse<FileModel> findById (Long id) {
        Optional<FileModel> optional = fileModelDao.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(ServerResponse::createBySuccessNullValue);
    }

    @Override
    public ServerResponse <List<FileModel>> findByIdIn (Iterable<Long> iterable) {
        return ServerResponse.createBySuccessWithData(fileModelDao.findByIdIn(iterable));
    }

    @Override
    public ServerResponse <FileModel> saveFile (FileModel fileModel) {
        return ServerResponse.createBySuccessWithData(fileModelDao.save(fileModel));
    }

    @Override
    public ServerResponse <List<FileModel>> saveFiles (List<FileModel> list) {
        if (Objects.isNull(list)) {
            return ServerResponse.createBySuccessWithData(Collections.emptyList());
        }
        // 执行更新操作
        List<FileModel> all = fileModelDao.saveAll(list);
        return ServerResponse.createBySuccessWithData(all);
    }

    @Override
    public ServerResponse <List<FileModel>> findAll (FileModel fileModel) {
        return ServerResponse.createBySuccessWithData(fileModelDao.findAll(Example.of(fileModel)));
    }

    @Override
    public ServerResponse <List<FileModel>> getFilesByFolderId(Long id) {
        FileModel fileModel = FileModel.builder()
                .id(id).deleted(false).build();
        return ServerResponse.createBySuccessWithData(fileModelDao.findAll(Example.of(fileModel)));
    }

    @Override
    public ServerResponse <List<FileModel>> deleteFilesInRecycleBin (Long memberId , HttpSession session) throws ExecutionException, InterruptedException {
        ServerResponse<Member> serverResponse = memberClientService.findOne(Member.builder().id(memberId).build());
        if (ServerResponse.isSuccess(serverResponse)) {
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
            Member member = serverResponse.getData();
            double size = member.getUsedStorageSpace() - future.get();
            member.setUsedStorageSpace(size);
            ServerResponse<Member> response = memberClientService.saveMember(member);
            if (ServerResponse.isSuccess(response)) {
                session.setAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName(), response.getData());
            }
            return ServerResponse.createBySuccessWithData(result);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_NOT_EXIST.getMessage());
        }
    }
}
