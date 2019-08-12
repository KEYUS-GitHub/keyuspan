package org.keyus.project.keyuspan.folder.provider.service;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-28  下午3:34
 */
public interface VirtualFolderService {

    ServerResponse<VirtualFolder> findById (Long id);

    ServerResponse <List<VirtualFolder>> findAll (VirtualFolder virtualFolder);

    ServerResponse <VirtualFolder> save (VirtualFolder virtualFolder);

    ServerResponse <List<VirtualFolder>> saveAll (List<VirtualFolder> virtualFolders);

    ServerResponse <List<VirtualFolder>> deleteFoldersInRecycleBin ();

    ServerResponse <List<VirtualFolder>> findByIdIn (Iterable<Long> iterable);

    String getVirtualPath (Long id);
}
