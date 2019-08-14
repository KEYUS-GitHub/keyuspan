package org.keyus.project.keyuspan.api.client.service.file;

import org.keyus.project.keyuspan.api.po.FileModel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author keyus
 * @create 2019-07-29  下午1:46
 */
@FeignClient(value = "keyuspan-file-provider")
public interface FileClientService {

    @PostMapping("/find_by_id")
    FileModel findById (@RequestBody Long id);

    @PostMapping("/find_by_id_in")
    List<FileModel> findByIdIn (@RequestBody Iterable<Long> iterable);

    @PostMapping("/save_file")
    FileModel saveFile (@RequestBody FileModel fileModel);

    @PostMapping("/save_files")
    List<FileModel> saveFiles (@RequestBody List<FileModel> list);

    @PostMapping("/find_all")
    List<FileModel> findAll (@RequestBody FileModel fileModel);

    @PostMapping("/get_files_by_folder_id")
    List<FileModel> getFilesByFolderId(@RequestBody Long id);

    @PostMapping("/delete_in_recycle_bin")
    List<FileModel> deleteFilesInRecycleBin (@RequestBody Long memberId) throws ExecutionException, InterruptedException;

    @PostMapping("/get_web_server_url")
    String getWebServerUrl();

    @PostMapping("/upload_file")
    String uploadFile (@RequestBody MultipartFile file) throws IOException;

    @PostMapping("/upload_files")
    String[] uploadFiles (@RequestBody List<MultipartFile> files) throws IOException;
}
