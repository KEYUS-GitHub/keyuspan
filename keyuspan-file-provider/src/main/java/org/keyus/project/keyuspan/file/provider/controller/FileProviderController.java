package org.keyus.project.keyuspan.file.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.PasswordToIdUtil;
import org.keyus.project.keyuspan.api.util.FileDownloadProxyUtil;
import org.keyus.project.keyuspan.api.util.FileModelUtil;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.provider.service.FileModelService;
import org.keyus.project.keyuspan.file.provider.service.FileService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/upload_file")
    public ServerResponse <FileModel> uploadFile (@RequestParam("file") MultipartFile file, @RequestParam("folder_id") Long folderId, HttpSession session) throws IOException {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        String uri = fileService.uploadFile(file);
        // TODO: 19-7-29 添加上传完成后，会员已经使用的存储空间增加的业务逻辑
        FileModel fileModel = FileModelUtil.changeToFileModel(member.getId(), file, uri, folderId);
        FileModel save = fileModelService.save(fileModel);
        return ServerResponse.createBySuccessWithData(save);
    }

    @PostMapping("/upload_files")
    public ServerResponse <List<FileModel>> uploadFiles (@RequestParam("files") MultipartFile[] files, @RequestParam("folder_id") Long folderId, HttpSession session) throws Exception {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        List<MultipartFile> list = new ArrayList<>(Arrays.asList(files));
        String[] uris = fileService.uploadFiles(list);
        // TODO: 19-7-29 添加上传完成后，会员已经使用的存储空间增加的业务逻辑
        List<FileModel> fileModels = FileModelUtil.changeToFileModels(member.getId(), list, uris, folderId);
        List<FileModel> saveAll = fileModelService.saveAll(fileModels);
        return ServerResponse.createBySuccessWithData(saveAll);
    }

    @PostMapping("/get_files_by_folder_id")
    public ServerResponse <List<FileModel>> getFilesByFolderId(@RequestParam("id") Long id) {
        FileModel fileModel = new FileModel();
        fileModel.setFolderId(id);
        fileModel.setDeleted(false);
        return ServerResponse.createBySuccessWithData(fileModelService.findAll(Example.of(fileModel)));
    }

    @PostMapping("/update_file_name")
    public ServerResponse <FileModel> updateFileName (@RequestParam("id") Long id, @RequestParam("newFileName") String newFileName) {
        Optional<FileModel> optional = fileModelService.findById(id);
        if (optional.isPresent()) {
            // 获取文件扩展名
            FileModel fileModel = optional.get();
            // 截取文件扩展名（不含有.）
            String fileExtension = newFileName.substring(newFileName.indexOf('.') + 1);

            // 修改文件名和文件扩展名
            fileModel.setFileExtension(fileExtension);
            // newFileName里包含有扩展名
            fileModel.setFileName(newFileName);
            // 修改文件的修改日期
            fileModel.setUpdateDate(LocalDate.now());
            // 更新数据
            FileModel save = fileModelService.save(fileModel);
            return ServerResponse.createBySuccessWithData(save);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("/download_file")
    public void downloadFile (@RequestParam("key") String key, HttpServletResponse response) throws FileDownloadException, IOException {
        // TODO: 19-7-30 实现文件下载的业务逻辑，要求是前端通过一个加密的字符串值
        //  解析出文件模型的ID，然后获取uri，执行下载，前端的请求参数没有文件
        //  的ID值，而是通过ID值加密出一个字符串值，后端再解密出ID，然后通过代
        //  理，代替用户的客户端去下载文件，需要检测权限。
        Long id = PasswordToIdUtil.decrypt(key);
        Optional<FileModel> optional = fileModelService.findById(id);
        if (optional.isPresent()) {
            FileModel fileModel = optional.get();
            String url = FileDownloadProxyUtil.getRealUrl(fileService.getWebServerUrl(), fileModel.getUri());
            FileDownloadProxyUtil.proxyAndDownload(response, url, null, fileModel.getFileName());
        } else {
            // TODO: 19-7-31 抛出异常之后跳转至一个提示页面
            throw new FileDownloadException(ErrorMessageEnum.FILE_DOWNLOAD_EXCEPTION.getMessage());
        }
    }

    @PostMapping("/delete_file_by_id")
    public ServerResponse <FileModel> deleteFileById (@RequestParam("id") Long id, HttpSession session) {
        Optional<FileModel> optional = fileModelService.findById(id);
        if (optional.isPresent()) {
            // 获取该会员回收站保留文件的天数
            Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
            Integer collectionDays = member.getGarbageCollectionDays();
            // 修改文件模型对象的值
            FileModel fileModel = optional.get();
            fileModel.setDeleted(true);
            fileModel.setDateOfRecovery(LocalDate.now().plusDays(collectionDays));
            // 更新数据
            FileModel save = fileModelService.save(fileModel);
            return ServerResponse.createBySuccessWithData(save);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("/share_file")
    public ServerResponse shareFile (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 给他人产生一个共享文件夹的链接
        return null;
    }

    @PostMapping("/save_file_by_share")
    public ServerResponse <FileModel> saveFileByShare (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 通过他人共享的链接来保存分享的文件夹
        return null;
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
}
