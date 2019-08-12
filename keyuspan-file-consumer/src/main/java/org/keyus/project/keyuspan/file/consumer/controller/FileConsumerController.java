package org.keyus.project.keyuspan.file.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.vo.FileModelVO;
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
    public ServerResponse uploadFile (@RequestParam("file") MultipartFile file, @RequestParam("folder_id") Long folderId, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        ServerResponse<FileModel> serverResponse = fileConsumerService.uploadFile(file, member, folderId);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(FileModelVO.getInstance(serverResponse.getData()));
        } else {
            return serverResponse;
        }
    }

    @PostMapping("/upload_files")
    public ServerResponse uploadFiles (@RequestParam("files") MultipartFile[] files, @RequestParam("folder_id") Long folderId, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        ServerResponse<List<FileModel>> serverResponse = fileConsumerService.uploadFiles(files, folderId, member);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(FileModelVO.getInstances(serverResponse.getData()));
        } else {
            return serverResponse;
        }
    }

    @PostMapping("/download_file")
    public byte[] downloadFile (@RequestParam("key") String key, HttpServletResponse response, HttpSession session) throws FileDownloadException, IOException {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        return fileConsumerService.downloadFile(key, response, member);
    }

    @PostMapping("/get_files_by_folder_id")
    public ServerResponse getFilesByFolderId(@RequestParam("id") Long id) {
        ServerResponse<List<FileModel>> serverResponse = fileConsumerService.getFilesByFolderId(id);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(FileModelVO.getInstances(serverResponse.getData()));
        } else {
            return serverResponse;
        }
    }

    @PostMapping("/update_file_name")
    public ServerResponse updateFileName (@RequestParam("id") Long id, @RequestParam("newFileName") String newFileName) {
        ServerResponse<FileModel> serverResponse = fileConsumerService.updateFileName(id, newFileName);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(FileModelVO.getInstance(serverResponse.getData()));
        } else {
            return serverResponse;
        }
    }

    @PostMapping("/delete_file_by_id")
    public ServerResponse deleteFileById (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        ServerResponse<FileModel> serverResponse = fileConsumerService.deleteFileById(id, member);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(FileModelVO.getInstance(serverResponse.getData()));
        } else {
            return serverResponse;
        }
    }
}
