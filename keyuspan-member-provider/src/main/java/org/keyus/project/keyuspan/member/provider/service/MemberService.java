package org.keyus.project.keyuspan.member.provider.service;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:05
 */
public interface MemberService {

    ServerResponse<List<Member>> getMembers();

    ServerResponse <Member> saveMember(Member member);

    ServerResponse <Member> findOne(Member member);

    ServerResponse <List<Long>> getMemberIdList ();
}
