package org.keyus.project.keyuspan.member.provider.dao;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-17  下午12:55
 */
public interface MemberDao extends JpaRepository<Member, Long> {

    @Override
    Optional<Member> findById(Long id);

    @Override
    <S extends Member> Optional<S> findOne(Example<S> example);

    @Override
    List<Member> findAll();

    @Override
    <S extends Member> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Member> S save(S entity);

    @Override
    <S extends Member> List<S> saveAll(Iterable<S> iterable);
}
