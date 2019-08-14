package org.keyus.project.keyuspan.file.provider.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

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

    @PostMapping("/find_by_id")
    public FileModel findById (@RequestBody Long id) {
        return fileModelService.findById(id).orElseGet(FileModel::new);
    }

    @PostMapping("/find_by_id_in")
    public List<FileModel> findByIdIn (@RequestBody Iterable<Long> iterable) {
        return fileModelService.findByIdIn(iterable);
    }

    @PostMapping("/save_file")
    public FileModel saveFile (@RequestBody FileModel fileModel) {
        return fileModelService.saveFile(fileModel);
    }

    @PostMapping("/save_files")
    public List<FileModel> saveFiles (@RequestBody List<FileModel> list) {
        return fileModelService.saveFiles(list);
    }

    @PostMapping("/find_all")
    public List<FileModel> findAll (@RequestBody FileModel fileModel) {
        return fileModelService.findAll(fileModel);
    }

    @PostMapping("/get_files_by_folder_id")
    public List<FileModel> getFilesByFolderId(@RequestBody Long id) {
        return fileModelService.getFilesByFolderId(id);
    }

    @PostMapping("/delete_in_recycle_bin")
    public List<FileModel> deleteFilesInRecycleBin (@RequestBody Long memberId , HttpSession session) throws ExecutionException, InterruptedException {
        return fileModelService.deleteFilesInRecycleBin(memberId, session);
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
}
