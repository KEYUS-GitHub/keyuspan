package org.keyus.project.keyuspan.member.provider.service;

import org.keyus.project.keyuspan.api.pojo.Member;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:05
 */
public interface MemberService {

    List<Member> getMembers();

    Member save(Member member);
}
