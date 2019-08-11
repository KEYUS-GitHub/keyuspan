package org.keyus.project.keyuspan.file.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.consumer.service.FileConsumerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-04  下午3:25
 */
@RestController
@AllArgsConstructor
public class FileConsumerController {

    private final FileConsumerService fileConsumerService;

    @PostMapping("/upload_file")
    public ServerResponse<FileModel> uploadFile (@RequestParam("file") MultipartFile file, @RequestParam("folder_id") Long folderId, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return fileConsumerService.uploadFile(file, member, folderId);
    }

    @PostMapping("/upload_files")
    public ServerResponse <List<FileModel>> uploadFiles (@RequestParam("files") MultipartFile[] files, @RequestParam("folder_id") Long folderId, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return fileConsumerService.uploadFiles(files, folderId, member);
    }

    @PostMapping("/download_file")
    public byte[] downloadFile (@RequestParam("key") String key, HttpServletResponse response, HttpSession session) throws FileDownloadException, IOException {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return fileConsumerService.downloadFile(key, response, member);
    }

    @PostMapping("/get_files_by_folder_id")
    public ServerResponse <List<FileModel>> getFilesByFolderId(@RequestParam("id") Long id) {
        return fileConsumerService.getFilesByFolderId(id);
    }

    @PostMapping("/update_file_name")
    public ServerResponse <FileModel> updateFileName (@RequestParam("id") Long id, @RequestParam("newFileName") String newFileName) {
        return fileConsumerService.updateFileName(id, newFileName);
    }

    @PostMapping("/delete_file_by_id")
    public ServerResponse <FileModel> deleteFileById (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return fileConsumerService.deleteFileById(id, member);
    }
}
