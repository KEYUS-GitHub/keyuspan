package org.keyus.project.keyuspan.folder.provider.service.impl;

import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.folder.provider.dao.VirtualFileFolderDao;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * @author keyus
 * @create 2019-07-28  下午3:35
 */
@Service
@AllArgsConstructor
public class VirtualFolderServiceImpl implements VirtualFolderService, ITxTransaction {

    private final VirtualFileFolderDao virtualFileFolderDao;

    @Override
    public VirtualFolder findById (Long id) {
        Optional<VirtualFolder> optional = virtualFileFolderDao.findById(id);
        return optional.orElseGet(VirtualFolder::new);
    }

    @Override
    public List<VirtualFolder> findAll (VirtualFolder virtualFolder) {
        return virtualFileFolderDao.findAll(Example.of(virtualFolder));
    }

    @TxTransaction
    @Transactional
    @Override
    public VirtualFolder save (VirtualFolder virtualFolder) {
        return virtualFileFolderDao.save(virtualFolder);
    }

    @TxTransaction
    @Transactional
    @Override
    public List<VirtualFolder> saveAll (List<VirtualFolder> virtualFolders) {
        return virtualFileFolderDao.saveAll(virtualFolders);
    }

    @TxTransaction
    @Transactional
    @Override
    public List<VirtualFolder> deleteFoldersInRecycleBin () {
        VirtualFolder folder = VirtualFolder.builder().deleted(true)
                .dateOfRecovery(LocalDate.now()).build();
        // 查询
        List<VirtualFolder> all = virtualFileFolderDao.findAll(Example.of(folder));
        virtualFileFolderDao.deleteInBatch(all);
        return all;
    }

    @Override
    public List<VirtualFolder> findByIdIn (Iterable<Long> iterable) {
        return virtualFileFolderDao.findByIdIn(iterable);
    }

    @Override
    public String getVirtualPath(Long id) {
        LinkedList<String> nameList = new LinkedList<>();
        getVirtualPath(id, nameList);
        StringBuilder builder = new StringBuilder();
        for (String name : nameList) {
            builder.append('/');
            builder.append(name);
        }
        return builder.toString();
    }

    private void getVirtualPath(Long id, LinkedList<String> nameList) {
        if (Objects.isNull(id) || Objects.isNull(nameList)) {
            return;
        }

        Optional<VirtualFolder> optional = virtualFileFolderDao.findById(id);
        if (optional.isPresent()) {
            VirtualFolder folder = optional.get();
            nameList.addFirst(folder.getVirtualFolderName());
            getVirtualPath(folder.getFatherFolderId(), nameList);
        }
    }
}
