package org.keyus.project.keyuspan.api.client.service.folder;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author keyus
 * @create 2019-08-02  下午3:13
 */
@FeignClient(value = "keyuspan-folder-provider", fallbackFactory = FolderClientServiceFallbackFactory.class)
public interface FolderClientService {

    @PostMapping("/create_main_folder")
    ServerResponse<VirtualFolder> createMainFolder (@RequestParam("memberId") Long memberId);

    @PostMapping("/find_by_id")
    ServerResponse <VirtualFolder> findById (@RequestParam("id") Long id);

    @PostMapping("/find_all")
    ServerResponse <List<VirtualFolder>> findAll (@RequestBody VirtualFolder virtualFolder);

    @PostMapping("/save")
    ServerResponse <VirtualFolder> save (@RequestBody VirtualFolder virtualFolder);

    @PostMapping("/save_all")
    ServerResponse <List<VirtualFolder>> saveAll (@RequestBody List<VirtualFolder> virtualFolders);
}
