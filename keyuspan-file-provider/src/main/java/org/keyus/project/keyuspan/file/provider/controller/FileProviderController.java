package org.keyus.project.keyuspan.file.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author keyus
 * @create 2019-07-25  下午2:13
 */
@RestController
@AllArgsConstructor
public class FileProviderController {

    private final FileModelService fileModelService;

    private final FileService fileService;

    @PostMapping("/find_by_id")
    public ServerResponse <FileModel> findById (@RequestParam("id") Long id) {
        Optional<FileModel> optional = fileModelService.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(ServerResponse::createBySuccessNullValue);
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
    public void deleteFilesInRecycleBin () {
        FileModel fileModel = new FileModel();
        fileModel.setDeleted(true);
        fileModel.setDateOfRecovery(LocalDate.now());
        List<FileModel> all = fileModelService.findAll(Example.of(fileModel));
        fileModelService.deleteInBatch(all);
    }
}
