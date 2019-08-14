package org.keyus.project.keyuspan.folder.provider.service;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import java.util.List;

/**
 * @author keyus
 * @create 2019-07-28  下午3:34
 */
public interface VirtualFolderService {

    VirtualFolder findById (Long id);

    List<VirtualFolder> findAll (VirtualFolder virtualFolder);

    VirtualFolder save (VirtualFolder virtualFolder);

    List<VirtualFolder> saveAll (List<VirtualFolder> virtualFolders);

    List<VirtualFolder> deleteFoldersInRecycleBin ();

    List<VirtualFolder> findByIdIn (Iterable<Long> iterable);

    String getVirtualPath (Long id);
}
