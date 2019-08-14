package org.keyus.project.keyuspan.member.provider.service;

import org.keyus.project.keyuspan.api.po.Member;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:05
 */
public interface MemberService {

    List<Member> getMembers();

    Member saveMember(Member member);

    Member findOne(Member member);

    List<Long> getMemberIdList ();
}
