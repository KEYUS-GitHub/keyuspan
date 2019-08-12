package org.keyus.project.keyuspan.file.consumer.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.exception.FileDownloadException;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.FileDownloadProxyUtil;
import org.keyus.project.keyuspan.api.util.FileModelUtil;
import org.keyus.project.keyuspan.api.util.PasswordToIdUtil;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.file.consumer.service.FileConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-11  下午11:19
 */
@Service
@Transactional
@AllArgsConstructor
public class FileConsumerServiceImpl implements FileConsumerService {

    private final FileClientService fileClientService;

    private final MemberClientService memberClientService;

    @Override
    public ServerResponse<FileModel> uploadFile(MultipartFile file, Member member, Long folderId) throws IOException {
        String uri = fileClientService.uploadFile(file);
        FileModel fileModel = FileModelUtil.changeToFileModel(member.getId(), file, uri, folderId);
        double fileSize = fileModel.getSize() / 1024;
        if (fileSize + member.getUsedStorageSpace() > member.getTotalStorageSpace()) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.OUT_OF_MEMBER_STORAGE_SPACE.getMessage());
        }
        // 否则
        FileModel save = fileClientService.saveFile(fileModel).getData();
        // 计算已经使用的size，单位是KB
        Double size = fileSize + member.getUsedStorageSpace();
        member.setUsedStorageSpace(size);
        memberClientService.saveMember(member);
        return ServerResponse.createBySuccessWithData(save);
    }

    @Override
    public ServerResponse <List<FileModel>> uploadFiles (MultipartFile[] files, Long folderId, Member member) throws Exception {
        List<MultipartFile> list = new ArrayList<>(Arrays.asList(files));
        String[] uris = fileClientService.uploadFiles(list);
        List<FileModel> fileModels = FileModelUtil.changeToFileModels(member.getId(), list, uris, folderId);

        // 计算空间总和
        double fileSize = 0.0;
        for (FileModel fileModel : fileModels) {
            fileSize += (fileModel.getSize() / 1024);
        }

        if (fileSize + member.getUsedStorageSpace() > member.getTotalStorageSpace()) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.OUT_OF_MEMBER_STORAGE_SPACE.getMessage());
        }

        return fileClientService.saveFiles(fileModels);
    }

    @Override
    public byte[] downloadFile(String key, HttpServletResponse response, Member member) throws FileDownloadException, IOException {
        Long id = PasswordToIdUtil.decrypt(key);
        ServerResponse<FileModel> serverResponse = fileClientService.findById(id);
        if (ServerResponse.isError(serverResponse) || ServerResponse.isNullValue(serverResponse)) {
            throw new FileDownloadException(ErrorMessageEnum.FILE_DOWNLOAD_EXCEPTION.getMessage());
        }
        FileModel fileModel = serverResponse.getData();
        // 文件权限校验，合理的请求才给予下载
        if (FileModelUtil.isBelongsToMember(member, fileModel)) {
            String url = FileDownloadProxyUtil.getRealUrl(fileClientService.getWebServerUrl(), fileModel.getUri());
            return FileDownloadProxyUtil.proxyAndDownload(response, url, null, fileModel.getFileName());
        }
        throw new FileDownloadException(ErrorMessageEnum.FILE_DOWNLOAD_EXCEPTION.getMessage());
    }

    @Override
    public ServerResponse <List<FileModel>> getFilesByFolderId(Long id) {
        return fileClientService.getFilesByFolderId(id);
    }

    @Override
    public ServerResponse <FileModel> updateFileName (Long id, String newFileName) {
        ServerResponse<FileModel> response = fileClientService.findById(id);
        if (ServerResponse.isError(response)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
        } else if (ServerResponse.isNullValue(response)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
        }
        // 获取文件扩展名
        FileModel fileModel = response.getData();
        // 截取文件扩展名（不含有.）
        String fileExtension = newFileName.substring(newFileName.indexOf('.') + 1);

        // 修改文件名和文件扩展名
        fileModel.setFileExtension(fileExtension);
        // newFileName里包含有扩展名
        fileModel.setFileName(newFileName);
        // 修改文件的修改日期
        fileModel.setUpdateDate(LocalDate.now());
        // 更新数据
        return fileClientService.saveFile(fileModel);
    }

    @Override
    public ServerResponse <FileModel> deleteFileById (Long id, Member member) {
        ServerResponse<FileModel> response = fileClientService.findById(id);
        if (ServerResponse.isError(response)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
        } else if (ServerResponse.isNullValue(response)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FILE_NOT_EXIST.getMessage());
        }
        // 获取该会员回收站保留文件的天数
        Integer collectionDays = member.getGarbageCollectionDays();
        // 修改文件模型对象的值
        FileModel fileModel = response.getData();
        fileModel.setDeleted(true);
        fileModel.setDateOfRecovery(LocalDate.now().plusDays(collectionDays));
        // 更新数据
        return fileClientService.saveFile(fileModel);
    }
}