package org.keyus.project.keyuspan.folder.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author keyus
 * @create 2019-07-28  下午3:51
 */
@RestController
@AllArgsConstructor
public class FolderProviderController {

    private final VirtualFolderService virtualFolderService;

    @PostMapping("/find_by_id")
    public VirtualFolder findById (@RequestBody Long id) {
        return virtualFolderService.findById(id);
    }

    @PostMapping("/find_all")
    public List<VirtualFolder> findAll (@RequestBody VirtualFolder virtualFolder) {
        return virtualFolderService.findAll(virtualFolder);
    }

    @PostMapping("/save")
    public VirtualFolder save (@RequestBody VirtualFolder virtualFolder) {
        return virtualFolderService.save(virtualFolder);
    }

    @PostMapping("/save_all")
    public List<VirtualFolder> saveAll (@RequestBody List<VirtualFolder> virtualFolders) {
        return virtualFolderService.saveAll(virtualFolders);
    }

    @PostMapping("/delete_in_recycle_bin")
    public List<VirtualFolder> deleteFoldersInRecycleBin () {
        return virtualFolderService.deleteFoldersInRecycleBin();
    }

    @PostMapping("/find_by_id_in")
    public List<VirtualFolder> findByIdIn (@RequestBody Iterable<Long> iterable) {
        return virtualFolderService.findByIdIn(iterable);
    }

    @PostMapping("/get_virtual_path")
    public String getVirtualPath(@RequestBody Long id) {
        return virtualFolderService.getVirtualPath(id);
    }
}
