package org.keyus.project.keyuspan.folder.provider.service.impl;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.keyus.project.keyuspan.folder.provider.dao.VirtualFileFolderDao;
import org.keyus.project.keyuspan.folder.provider.service.VirtualFolderService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-28  下午3:35
 */
@Service
@Transactional
public class VirtualFolderServiceImpl implements VirtualFolderService {

    private final VirtualFileFolderDao virtualFileFolderDao;

    public VirtualFolderServiceImpl(VirtualFileFolderDao virtualFileFolderDao) {
        this.virtualFileFolderDao = virtualFileFolderDao;
    }

    @Override
    public Optional<VirtualFolder> findById(Long id) {
        return virtualFileFolderDao.findById(id);
    }

    @Override
    public List<VirtualFolder> findAll() {
        return virtualFileFolderDao.findAll();
    }

    @Override
    public List<VirtualFolder> findAll(Sort sort) {
        return virtualFileFolderDao.findAll(sort);
    }

    @Override
    public <S extends VirtualFolder> List<S> findAll(Example<S> example) {
        return virtualFileFolderDao.findAll(example);
    }

    @Override
    public <S extends VirtualFolder> List<S> findAll(Example<S> example, Sort sort) {
        return virtualFileFolderDao.findAll(example, sort);
    }

    @Override
    public <S extends VirtualFolder> Page<S> findAll(Example<S> example, Pageable pageable) {
        return virtualFileFolderDao.findAll(example, pageable);
    }

    @Override
    public <S extends VirtualFolder> S save(S s) {
        return virtualFileFolderDao.save(s);
    }

    @Override
    public <S extends VirtualFolder> List<S> saveAll(Iterable<S> iterable) {
        return virtualFileFolderDao.saveAll(iterable);
    }

    @Override
    public void deleteById(Long id) {
        virtualFileFolderDao.deleteById(id);
    }

    @Override
    public void delete(VirtualFolder virtualFolder) {
        virtualFileFolderDao.delete(virtualFolder);
    }

    @Override
    public void deleteInBatch(Iterable<VirtualFolder> iterable) {
        virtualFileFolderDao.deleteInBatch(iterable);
    }
}
