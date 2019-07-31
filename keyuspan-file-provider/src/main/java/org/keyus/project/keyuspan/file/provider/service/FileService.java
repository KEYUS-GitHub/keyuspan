package org.keyus.project.keyuspan.file.provider.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-22  下午10:06
 * 进行实际文件操作的service
 */
public interface FileService {

    String uploadFile (MultipartFile file) throws IOException;

    String[] uploadFiles (List<MultipartFile> files) throws IOException;

    String uploadFile (File file) throws IOException;

    String uploadFile (String content, String fileExtension);

    void deleteFile (String fileUrl);

    String getWebServerUrl();
}