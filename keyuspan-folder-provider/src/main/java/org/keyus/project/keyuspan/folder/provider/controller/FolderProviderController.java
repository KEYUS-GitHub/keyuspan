package org.keyus.project.keyuspan.folder.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author keyus
 * @create 2019-07-28  下午3:51
 */
@RestController
@AllArgsConstructor
public class FolderProviderController {

    private final VirtualFolderService virtualFolderService;

    private final MemberClientService memberClientService;

    @Resource(name = "folderProviderExecutor")
    private ThreadPoolExecutor executor;

    @PostMapping("/find_by_id")
    public ServerResponse <VirtualFolder> findById (@RequestParam("id") Long id) {
        Optional<VirtualFolder> optional = virtualFolderService.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(() -> ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage()));
    }

    @PostMapping("/find_all")
    public ServerResponse <List<VirtualFolder>> findAll (@RequestBody VirtualFolder virtualFolder) {
        return ServerResponse.createBySuccessWithData(virtualFolderService.findAll(Example.of(virtualFolder)));
    }

    @PostMapping("/save")
    public ServerResponse <VirtualFolder> save (@RequestBody VirtualFolder virtualFolder) {
        VirtualFolder save = virtualFolderService.save(virtualFolder);
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @PostMapping("/save_all")
    public ServerResponse <List<VirtualFolder>> saveAll (@RequestBody List<VirtualFolder> virtualFolders) {
        return ServerResponse.createBySuccessWithData(virtualFolderService.saveAll(virtualFolders));
    }

    @PostMapping("/delete_in_recycle_bin")
    public ServerResponse <List<VirtualFolder>> deleteFoldersInRecycleBin () {
        VirtualFolder folder = VirtualFolder.builder().deleted(true)
                .dateOfRecovery(LocalDate.now()).build();
        // 查询
        List<VirtualFolder> all = virtualFolderService.findAll(Example.of(folder));
        virtualFolderService.deleteInBatch(all);
        return ServerResponse.createBySuccessWithData(all);
    }
}
