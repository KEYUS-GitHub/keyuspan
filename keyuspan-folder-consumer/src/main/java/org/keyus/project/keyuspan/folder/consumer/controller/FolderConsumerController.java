package org.keyus.project.keyuspan.folder.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.client.service.folder.FolderClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VirtualFolderUtil;
import org.keyus.project.keyuspan.api.vo.FolderMessageVO;
import org.keyus.project.keyuspan.api.vo.FolderVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author keyus
 * @create 2019-08-04  下午11:23
 */
@RestController
@AllArgsConstructor
public class FolderConsumerController {

    private final FolderClientService folderClientService;

    private final FileClientService fileClientService;

    @PostMapping("/open_folder")
    public ServerResponse openFolderById (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());

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

    @PostMapping("/create_folder")
    public ServerResponse <VirtualFolder> createFolder (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
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

    @PostMapping("/create_main_folder")
    public ServerResponse <VirtualFolder> createMainFolder (@RequestParam("memberId") Long memberId) {
        return folderClientService.save(VirtualFolderUtil.createMainVirtualFolder(memberId));
    }

    @PostMapping("/update_folder_name")
    public ServerResponse <VirtualFolder> updateFolderName (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
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

    @PostMapping("/delete_folder")
    public ServerResponse <FolderMessageVO> deleteFolder (@RequestParam("id") Long id, HttpSession session) {

        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
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
        VirtualFolder virtualFolder = new VirtualFolder();
        virtualFolder.setFatherFolderId(id);
        virtualFolder.setDeleted(false);
        // 查询子目录的待删除的虚拟目录
        List<VirtualFolder> all = folderClientService.findAll(virtualFolder).getData();
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
