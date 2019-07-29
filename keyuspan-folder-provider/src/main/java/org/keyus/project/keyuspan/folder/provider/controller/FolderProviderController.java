package org.keyus.project.keyuspan.folder.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.FileModel;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.service.file.FileClientService;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VirtualFolderUtil;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.keyus.project.keyuspan.api.vo.VirtualFolderVO;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
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
    public ServerResponse openFileFolderById (@RequestBody Long id, HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        Optional<VirtualFolder> optional = virtualFolderService.findById(id);
        if (optional.isPresent() && VirtualFolderUtil.isBelongToThisMember(member, optional.get())) {
            VirtualFolder virtualFolder = new VirtualFolder();
            virtualFolder.setFatherFolderId(optional.get().getId());
            // 获得该文件夹目录下的所有的一级子文件夹
            List<VirtualFolder> virtualFolders = virtualFolderService.findAll(Example.of(virtualFolder));
            // 获得该文件夹目录下的所有的文件
            ServerResponse <List<FileModel>> response = fileClientService.getFilesByFolderId(optional.get().getId());
            return ServerResponse.createBySuccessWithData(VirtualFolderVO.getInstance(virtualFolders, response.getData()));
        } else {
            return ServerResponse.createByErrorWithMessage("该文件夹不存在");
        }
    }
}
