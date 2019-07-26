package org.keyus.project.keyuspan.file.provider.controller;

import org.keyus.project.keyuspan.api.pojo.FileModel;
import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.api.util.FileModelUtil;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-25  下午2:13
 */
@RestController
public class FileModelProviderController {

    private final FileModelService fileModelService;

    private final FileService fileService;

    public FileModelProviderController (FileModelService fileModelService, FileService fileService) {
        this.fileModelService = fileModelService;
        this.fileService = fileService;
    }

    @PostMapping("/upload_file")
    public ServerResponse uploadFile (@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute("member");
        String uri = fileService.uploadFile(file);
        FileModel fileModel = FileModelUtil.changeToFileModel(member.getId(), file, uri);
        FileModel save = fileModelService.save(fileModel);
        return ServerResponse.createBySuccessWithData(save);
    }

    @PostMapping("/upload_files")
    public ServerResponse uploadFiles (@RequestParam("files") MultipartFile[] files, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute("member");
        List<MultipartFile> list = new ArrayList<>(Arrays.asList(files));
        String[] uris = fileService.uploadFiles(list);
        List<FileModel> fileModels = FileModelUtil.changeToFileModels(member.getId(), list, uris);
        List<FileModel> saveAll = fileModelService.saveAll(fileModels);
        return ServerResponse.createBySuccessWithData(saveAll);
    }

}
