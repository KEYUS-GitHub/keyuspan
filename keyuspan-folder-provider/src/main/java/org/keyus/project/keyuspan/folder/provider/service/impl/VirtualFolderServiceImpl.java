package org.keyus.project.keyuspan.folder.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.api.util.ServerResponse;
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
@Transactional
@AllArgsConstructor
public class VirtualFolderServiceImpl implements VirtualFolderService {

    private final VirtualFileFolderDao virtualFileFolderDao;

    @Override
    public ServerResponse<VirtualFolder> findById (Long id) {
        Optional<VirtualFolder> optional = virtualFileFolderDao.findById(id);
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(() -> ServerResponse.createByErrorWithMessage(ErrorMessageEnum.FOLDER_NOT_EXIST.getMessage()));
    }

    @Override
    public ServerResponse <List<VirtualFolder>> findAll (VirtualFolder virtualFolder) {
        return ServerResponse.createBySuccessWithData(virtualFileFolderDao.findAll(Example.of(virtualFolder)));
    }

    @Override
    public ServerResponse <VirtualFolder> save (VirtualFolder virtualFolder) {
        VirtualFolder save = virtualFileFolderDao.save(virtualFolder);
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SAVE_FAIL_EXCEPTION.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @Override
    public ServerResponse <List<VirtualFolder>> saveAll (List<VirtualFolder> virtualFolders) {
        return ServerResponse.createBySuccessWithData(virtualFileFolderDao.saveAll(virtualFolders));
    }

    @Override
    public ServerResponse <List<VirtualFolder>> deleteFoldersInRecycleBin () {
        VirtualFolder folder = VirtualFolder.builder().deleted(true)
                .dateOfRecovery(LocalDate.now()).build();
        // 查询
        List<VirtualFolder> all = virtualFileFolderDao.findAll(Example.of(folder));
        virtualFileFolderDao.deleteInBatch(all);
        return ServerResponse.createBySuccessWithData(all);
    }

    @Override
    public ServerResponse <List<VirtualFolder>> findByIdIn (Iterable<Long> iterable) {
        return ServerResponse.createBySuccessWithData(virtualFileFolderDao.findByIdIn(iterable));
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
