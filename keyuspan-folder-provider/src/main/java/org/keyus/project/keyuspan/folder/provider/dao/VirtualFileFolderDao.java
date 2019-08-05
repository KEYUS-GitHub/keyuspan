package org.keyus.project.keyuspan.folder.provider.dao;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-27  下午11:11
 */
public interface VirtualFileFolderDao extends JpaRepository<VirtualFolder, Long> {

    @Override
    Optional<VirtualFolder> findById(Long id);

    @Override
    List<VirtualFolder> findAll();

    @Override
    List<VirtualFolder> findAll(Sort sort);

    @Override
    <S extends VirtualFolder> List<S> findAll(Example<S> example);

    @Override
    <S extends VirtualFolder> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends VirtualFolder> Page<S> findAll(Example<S> example, Pageable pageable);

    @Modifying
    @Override
    <S extends VirtualFolder> S save(S s);

    @Modifying
    @Override
    <S extends VirtualFolder> List<S> saveAll(Iterable<S> iterable);

    @Override
    void deleteById(Long id);

    @Override
    void delete(VirtualFolder virtualFolder);

    @Override
    void deleteInBatch(Iterable<VirtualFolder> iterable);
}
