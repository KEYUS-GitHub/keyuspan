package org.keyus.project.keyuspan.folder.consumer.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VirtualFolderUtil;
import org.keyus.project.keyuspan.api.vo.FolderMessageVO;
import org.keyus.project.keyuspan.api.vo.FolderVO;
import org.keyus.project.keyuspan.folder.consumer.service.FolderConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-12  下午1:34
 */
@Service
@Transactional
@AllArgsConstructor
public class FolderConsumerServiceImpl implements FolderConsumerService {

    private final FolderClientService folderClientService;

    private final FileClientService fileClientService;

    @Override
    public ServerResponse openFolderById (Long id, Member member) {
        ServerResponse<VirtualFolder> serverResponse = folderClientService.findById(id);
        if (ServerResponse.isSuccess(serverResponse)) {
            if (VirtualFolderUtil.isBelongToThisMember(member, serverResponse.getData())) {
                VirtualFolder folder = new VirtualFolder();
                folder.setFatherFolderId(serverResponse.getData().getId());
                // 获取未被删除的文件夹
                folder.setDeleted(false);

                // 获得该文件夹目录下的所有的一级子文件夹
                List<VirtualFolder> virtualFolders = folderClientService.findAll(folder).getData();
                // 获得该文件夹目录下的所有的文件
                ServerResponse <List<FileModel>> response = fileClientService.getFilesByFolderId(serverResponse.getData().getId());
                String path = folderClientService.getVirtualPath(id).getData();
                return ServerResponse.createBySuccessWithData(FolderMessageVO.getInstance(FolderVO.getInstance(serverResponse.getData()),
                        path, virtualFolders, response.getData()));
            } else {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }
        } else {
            return serverResponse;
        }
    }

    @Override
    public ServerResponse <VirtualFolder> createFolder (Long id, String folderName, Member member) {
        ServerResponse<VirtualFolder> serverResponse = folderClientService.findById(id);
        if (ServerResponse.isSuccess(serverResponse)) {
            if (VirtualFolderUtil.isBelongToThisMember(member, serverResponse.getData())) {
                // 创建新文件夹
                VirtualFolder newVirtualFolder = VirtualFolderUtil.createNewVirtualFolder(member.getId(), id, folderName);
                // 保存文件夹记录
                return folderClientService.save(newVirtualFolder);
            } else {
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
            }
        } else {
            return serverResponse;
        }
    }

    @Override
    public ServerResponse <VirtualFolder> createMainFolder (Long memberId) {
        return folderClientService.save(VirtualFolderUtil.createMainVirtualFolder(memberId));
    }

    @Override
    public ServerResponse <VirtualFolder> updateFolderName (Long id, String folderName, Member member) {
        ServerResponse<VirtualFolder> serverResponse = folderClientService.findById(id);

        if (ServerResponse.isSuccess(serverResponse) && VirtualFolderUtil.isBelongToThisMember(member, serverResponse.getData())) {
            VirtualFolder virtualFolder = serverResponse.getData();
            virtualFolder.setVirtualFolderName(folderName);
            virtualFolder.setUpdateDate(LocalDate.now());

            // 执行更新
            return folderClientService.save(virtualFolder);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
        }
    }

    @Override
    public ServerResponse <FolderMessageVO> deleteFolder (Long id, Member member) {

        List<VirtualFolder> virtualFolders = new ArrayList<>();
        // 获取待删除的虚拟文件夹
        getVirtualFolderForDelete(virtualFolders, id);

        // 回收站回收这是文件夹和文件的日期
        LocalDate collectDate = LocalDate.now().plusDays(member.getGarbageCollectionDays());

        List<FileModel> fileModels = new ArrayList<>();
        for (VirtualFolder folder : virtualFolders) {
            getFileModelForDelete(fileModels, folder.getId());
            folder.setDeleted(true);
            // 设置vf的回收站回收日期
            folder.setDateOfRecovery(collectDate);
        }

        // 对虚拟文件夹保存更新
        ServerResponse<List<VirtualFolder>> serverResponse = folderClientService.saveAll(virtualFolders);

        for (FileModel fileModel : fileModels) {
            fileModel.setDeleted(true);
            fileModel.setDateOfRecovery(collectDate);
        }
        ServerResponse<List<FileModel>> response = fileClientService.saveFiles(fileModels);

        return ServerResponse.createBySuccessWithData(FolderMessageVO.getInstance(null,
                null, serverResponse.getData(), response.getData()));
    }

    /**
     * 使用入参的list递归调用，获取所有的要删除的虚拟文件夹
     */
    private void getVirtualFolderForDelete (List<VirtualFolder> list, Long id) {
        VirtualFolder folder = VirtualFolder
                .builder()
                .fatherFolderId(id)
                .deleted(false)
                .build();
        // 查询子目录的待删除的虚拟目录
        List<VirtualFolder> all = folderClientService.findAll(folder).getData();
        list.addAll(all);

        for (VirtualFolder vf : all) {
            getVirtualFolderForDelete(list, vf.getId());
        }
    }

    /**
     * 通过入参的list和目录的ID获取待删除的文件
     */
    private void getFileModelForDelete (List<FileModel> list, Long id) {
        ServerResponse<List<FileModel>> response = fileClientService.getFilesByFolderId(id);
        if (ServerResponse.isSuccess(response)) {
            list.addAll(response.getData());
        }
    }
}
