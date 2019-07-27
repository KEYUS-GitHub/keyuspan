package org.keyus.project.keyuspan.folder.provider.dao;

import org.keyus.project.keyuspan.api.pojo.VirtualFileFolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-27  下午11:11
 */
public interface VirtualFileFolderDao extends JpaRepository<VirtualFileFolder, Long> {

    @Override
    Optional<VirtualFileFolder> findById(Long id);

    @Override
    <S extends VirtualFileFolder> Page<S> findAll(Example<S> example, Pageable pageable);

    @Modifying
    @Override
    <S extends VirtualFileFolder> S save(S s);

    @Modifying
    @Override
    <S extends VirtualFileFolder> List<S> saveAll(Iterable<S> iterable);

    @Override
    void deleteById(Long id);

    @Override
    void delete(VirtualFileFolder virtualFileFolder);

    @Override
    void deleteAll();
}
