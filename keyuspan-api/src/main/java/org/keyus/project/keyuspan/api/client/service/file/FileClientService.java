package org.keyus.project.keyuspan.api.client.service.file;

import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午1:46
 */
@FeignClient(value = "keyuspan-file-provider", fallbackFactory = FileModelClientServiceFallbackFactory.class)
public interface FileClientService {

    @PostMapping("/upload_file")
    ServerResponse <FileModel> uploadFile (@RequestParam("file") MultipartFile file, @RequestParam("id") Long id, HttpSession session) throws IOException;

    @PostMapping("/upload_files")
    ServerResponse <List<FileModel>> uploadFiles (@RequestParam("files") MultipartFile[] files, @RequestParam("id") Long id, HttpSession session) throws Exception;

    @PostMapping("/get_files_by_folder_id")
    ServerResponse <List<FileModel>> getFilesByFolderId(@RequestParam("id") Long id);
}
