package org.keyus.project.keyuspan.folder.provider.service;

import org.keyus.project.keyuspan.api.po.VirtualFolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-28  下午3:34
 */
public interface VirtualFolderService {

    Optional<VirtualFolder> findById(Long id);

    List<VirtualFolder> findByIdIn(Iterable<Long> iterable);

    List<VirtualFolder> findAll();

    List<VirtualFolder> findAll(Sort sort);

    <S extends VirtualFolder> List<S> findAll(Example<S> example);

    <S extends VirtualFolder> List<S> findAll(Example<S> example, Sort sort);

    <S extends VirtualFolder> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends VirtualFolder> S save(S s);

    <S extends VirtualFolder> List<S> saveAll(Iterable<S> iterable);

    void deleteById(Long id);

    void delete(VirtualFolder virtualFolder);

    void deleteInBatch(Iterable<VirtualFolder> iterable);

    String getVirtualPath (Long id);
}
