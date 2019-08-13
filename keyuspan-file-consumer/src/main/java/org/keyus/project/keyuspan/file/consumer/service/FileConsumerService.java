package org.keyus.project.keyuspan.file.consumer.service;

import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-11  下午11:18
 */
public interface FileConsumerService {

    ServerResponse<FileModel> uploadFile(MultipartFile file, Member member, Long folderId) throws Throwable;

    ServerResponse <List<FileModel>> uploadFiles (MultipartFile[] files, Long folderId, Member member) throws Throwable;

    byte[] downloadFile (String key, HttpServletResponse response, Member member) throws Throwable;

    ServerResponse <List<FileModel>> getFilesByFolderId(Long id);

    ServerResponse <FileModel> updateFileName (Long id, String newFileName);

    ServerResponse <FileModel> deleteFileById (Long id, Member member);
}
