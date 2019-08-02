package org.keyus.project.keyuspan.folder.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.client.service.file.FileClientService;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VirtualFolderUtil;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.keyus.project.keyuspan.api.vo.VirtualFolderVO;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-28  下午3:51
 */
@RestController
@AllArgsConstructor
public class FolderProviderController {

    private final VirtualFolderService virtualFolderService;

    private final FileClientService fileClientService;

    @PostMapping("/open_folder")
    public ServerResponse <VirtualFolderVO> openFolderById (@RequestParam("id") Long id, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        Optional<VirtualFolder> optional = virtualFolderService.findById(id);
        if (optional.isPresent() && VirtualFolderUtil.isBelongToThisMember(member, optional.get())) {
            VirtualFolder virtualFolder = new VirtualFolder();
            virtualFolder.setFatherFolderId(optional.get().getId());
            // 获取未被删除的文件夹
            virtualFolder.setDeleted(false);

            // 获得该文件夹目录下的所有的一级子文件夹
            List<VirtualFolder> virtualFolders = virtualFolderService.findAll(Example.of(virtualFolder));
            // 获得该文件夹目录下的所有的文件
            ServerResponse <List<FileModel>> response = fileClientService.getFilesByFolderId(optional.get().getId());
            return ServerResponse.createBySuccessWithData(VirtualFolderVO.getInstance(virtualFolders, response.getData()));
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("/create_folder")
    public ServerResponse <VirtualFolder> createFolder (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        Optional<VirtualFolder> optional = virtualFolderService.findById(id);
        if (optional.isPresent() && VirtualFolderUtil.isBelongToThisMember(member, optional.get())) {
            // 创建新文件夹
            VirtualFolder newVirtualFolder = VirtualFolderUtil.createNewVirtualFolder(member.getId(), id, folderName, optional.get().getVirtualPath());
            // 保存文件夹记录
            VirtualFolder save = virtualFolderService.save(newVirtualFolder);
            return ServerResponse.createBySuccessWithData(save);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("/create_main_folder")
    public ServerResponse <VirtualFolder> createMainFolder (@RequestParam("memberId") Long memberId) {
        VirtualFolder save = virtualFolderService.save(VirtualFolderUtil.createMainVirtualFolder(memberId));
        return ServerResponse.createBySuccessWithData(save);
    }

    @PostMapping("/update_folder_name")
    public ServerResponse <VirtualFolder> updateFolderName (@RequestParam("id") Long id, @RequestParam("folderName") String folderName, HttpSession session) {
        Member member = (Member) session.getAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName());
        Optional<VirtualFolder> optional = virtualFolderService.findById(id);
        if (optional.isPresent() && VirtualFolderUtil.isBelongToThisMember(member, optional.get())) {
            // 获取旧的文件夹名和文件夹路径
            VirtualFolder virtualFolder = optional.get();
            String virtualFolderName = virtualFolder.getVirtualFolderName();
            String virtualPath = virtualFolder.getVirtualPath();

            // 修改文件夹名和文件夹路径
            // TODO: 19-8-2 修改现有的实现方式，此操作还需要
            //  递归地将所有子孙文件夹的文件夹路径执行修改操作
            virtualPath = virtualPath.substring(0, virtualPath.length() - virtualFolderName.length());
            virtualPath = virtualPath + folderName;
            virtualFolder.setVirtualFolderName(folderName);
            virtualFolder.setVirtualPath(virtualPath);
            virtualFolder.setUpdateDate(LocalDate.now());

            // 执行更新
            VirtualFolder save = virtualFolderService.save(virtualFolder);
            return ServerResponse.createBySuccessWithData(save);
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
        }
    }

    @PostMapping("/delete_folder")
    public ServerResponse <VirtualFolderVO> deleteFolder (@RequestParam("id") Long id, HttpSession session) {

        // 本系统中，ID参数如果为0，会导致所有数据递归删除，造成严重破坏
        // 因此需要检测数值进行保护
        if (Objects.equals(0L, id)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage());
        }

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
        virtualFolderService.saveAll(virtualFolders);

        for (FileModel fileModel : fileModels) {
            fileModel.setDeleted(true);
            fileModel.setDateOfRecovery(collectDate);
        }
        ServerResponse<List<FileModel>> response = fileClientService.saveFiles(fileModels);

        return ServerResponse.createBySuccessWithData(VirtualFolderVO.getInstance(virtualFolders, response.getData()));
    }

    @PostMapping("/share_folder")
    public ServerResponse shareFolder (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 给他人产生一个共享文件夹的链接
        return null;
    }

    @PostMapping("/save_folder_by_share")
    public ServerResponse <VirtualFolder> saveFolderByShare (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 通过他人共享的链接来保存分享的文件夹
        return null;
    }

    @PostMapping("/download_folder")
    public ServerResponse downloadFolder (@RequestParam("id") Long id, HttpSession session) {
        // TODO: 19-7-30 下载一个文件夹下所有的文件及文件夹
        return null;
    }

    /**
     * 以下接口暂时不实现
     */

    public ServerResponse copyFolder () {
        // TODO: 19-7-30 有余力的情况下实现文件夹的复制
        return null;
    }

    public ServerResponse pasteFolder () {
        // TODO: 19-7-30 有余力的情况下实现文件夹的粘贴
        return null;
    }

    public ServerResponse moveFolder () {
        // TODO: 19-7-30 有余力的情况下实现文件夹的移动
        return null;
    }

    /**
     * 使用入参的list递归调用，获取所有的要删除的虚拟文件夹
     */
    private void getVirtualFolderForDelete (List<VirtualFolder> list, Long id) {
        VirtualFolder virtualFolder = new VirtualFolder();
        virtualFolder.setFatherFolderId(id);
        virtualFolder.setDeleted(false);
        // 查询子目录的待删除的虚拟目录
        List<VirtualFolder> all = virtualFolderService.findAll(Example.of(virtualFolder));
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
