package org.keyus.project.keyuspan.member.provider.service;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-16  下午10:05
 */
public interface MemberService {

    List<Member> getMembers();

    <S extends Member> S save(S entity);

    <S extends Member> Optional<S> findOne(Example<S> example);
}
