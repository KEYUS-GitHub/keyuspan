package org.keyus.project.keyuspan.file.provider.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author keyus
 * @create 2019-07-25  下午2:13
 */
@Slf4j
@RestController
@AllArgsConstructor
public class FileProviderController {

    private final FileModelService fileModelService;

    private final FileService fileService;

    private final MemberClientService memberClientService;

    @Resource(name = "fileProviderExecutor")
    private ThreadPoolExecutor executor;

    @PostMapping("/find_by_id")
    public ServerResponse <FileModel> findById (@RequestParam("id") Long id) {
        Optional<FileModel> optional = fileModelService.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(ServerResponse::createBySuccessNullValue);
    }

    @PostMapping("/find_by_id_in")
    public ServerResponse <List<FileModel>> findByIdIn (@RequestBody Iterable<Long> iterable) {
        return ServerResponse.createBySuccessWithData(fileModelService.findByIdIn(iterable));
    }

    @PostMapping("/save_file")
    public ServerResponse <FileModel> saveFile (@RequestBody FileModel fileModel) {
        return ServerResponse.createBySuccessWithData(fileModelService.save(fileModel));
    }

    @PostMapping("/save_files")
    public ServerResponse <List<FileModel>> saveFiles (@RequestBody List<FileModel> list) {
        if (Objects.isNull(list)) {
            return ServerResponse.createBySuccessWithData(Collections.emptyList());
        }
        // 执行更新操作
        List<FileModel> all = fileModelService.saveAll(list);
        return ServerResponse.createBySuccessWithData(all);
    }

    @PostMapping("/get_web_server_url")
    public String getWebServerUrl() {
        return fileService.getWebServerUrl();
    }

    @PostMapping("/upload_file")
    public String uploadFile (@RequestBody MultipartFile file) throws IOException {
        return fileService.uploadFile(file);
    }

    @PostMapping("/upload_files")
    public String[] uploadFiles (@RequestBody List<MultipartFile> files) throws IOException {
        return fileService.uploadFiles(files);
    }

    @PostMapping("/find_all")
    public ServerResponse <List<FileModel>> findAll (@RequestBody FileModel fileModel) {
        return ServerResponse.createBySuccessWithData(fileModelService.findAll(Example.of(fileModel)));
    }

    @PostMapping("/get_files_by_folder_id")
    public ServerResponse <List<FileModel>> getFilesByFolderId(@RequestParam("id") Long id) {
        FileModel fileModel = new FileModel();
        fileModel.setFolderId(id);
        fileModel.setDeleted(false);
        return ServerResponse.createBySuccessWithData(fileModelService.findAll(Example.of(fileModel)));
    }

    @PostMapping("/delete_in_recycle_bin")
    public ServerResponse <List<FileModel>> deleteFilesInRecycleBin (@RequestBody Long memberId , HttpSession session) throws ExecutionException, InterruptedException {
        ServerResponse<Member> serverResponse = memberClientService.findOne(Member.builder().id(memberId).build());
        if (ServerResponse.isSuccess(serverResponse)) {
            final List<FileModel> result = new ArrayList<>();
            Future<Double> future = executor.submit(() -> {
                FileModel fileModel = FileModel.builder().memberId(memberId).deleted(true)
                        .dateOfRecovery(LocalDate.now()).build();
                // 根据会员ID查询该会员需要删除的文件模型记录
                List<FileModel> all = fileModelService.findAll(Example.of(fileModel));
                double size = 0.0;
                for (FileModel fm : all) {
                    size += fm.getSize();
                }
                result.addAll(all);
                // 执行删除
                executor.execute(() -> fileModelService.deleteInBatch(all));
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
