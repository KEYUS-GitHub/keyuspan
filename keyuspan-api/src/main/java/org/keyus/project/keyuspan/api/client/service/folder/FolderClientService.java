package org.keyus.project.keyuspan.api.client.service.folder;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-02  下午3:13
 */
@FeignClient(value = "keyuspan-folder-provider")
public interface FolderClientService {

    @PostMapping("/find_by_id")
    VirtualFolder findById (@RequestBody Long id);

    @PostMapping("/find_all")
    List<VirtualFolder> findAll (@RequestBody VirtualFolder virtualFolder);

    @PostMapping("/save")
    VirtualFolder save (@RequestBody VirtualFolder virtualFolder);

    @PostMapping("/save_all")
    List<VirtualFolder> saveAll (@RequestBody List<VirtualFolder> virtualFolders);

    @PostMapping("/delete_in_recycle_bin")
    List<VirtualFolder> deleteFoldersInRecycleBin ();

    @PostMapping("/find_by_id_in")
    List<VirtualFolder> findByIdIn (@RequestBody Iterable<Long> iterable);

    @PostMapping("/get_virtual_path")
    String getVirtualPath(@RequestBody Long id);

    @PostMapping("/create_main_folder")
    VirtualFolder createMainFolder (@RequestBody Long memberId);
}
