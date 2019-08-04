package org.keyus.project.keyuspan.api.client.service.file;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午1:46
 */
@FeignClient(value = "keyuspan-file-provider", fallbackFactory = FileModelClientServiceFallbackFactory.class)
public interface FileClientService {

    @PostMapping("/upload_file")
    String uploadFile (@RequestBody MultipartFile file) throws IOException;

    @PostMapping("/upload_files")
    String[] uploadFiles (@RequestBody List<MultipartFile> files) throws IOException;

    @PostMapping("/find_by_id")
    ServerResponse <FileModel> findById (@RequestParam("id") Long id);

    @PostMapping("/get_files_by_folder_id")
    ServerResponse <List<FileModel>> getFilesByFolderId(@RequestParam("id") Long id);

    @PostMapping("/save_file")
    ServerResponse <FileModel> saveFile (@RequestBody FileModel fileModel);

    @PostMapping("/save_files")
    ServerResponse <List<FileModel>> saveFiles (List<FileModel> list);

    @PostMapping("/get_web_server_url")
    String getWebServerUrl();

    @PostMapping("/find_all")
    ServerResponse <List<FileModel>> findAll (@RequestBody FileModel fileModel);
}
