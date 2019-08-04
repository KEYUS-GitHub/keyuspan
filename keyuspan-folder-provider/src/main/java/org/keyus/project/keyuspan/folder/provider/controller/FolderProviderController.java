package org.keyus.project.keyuspan.folder.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
