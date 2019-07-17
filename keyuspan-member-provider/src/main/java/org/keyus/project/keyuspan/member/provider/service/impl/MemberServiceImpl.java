package org.keyus.project.keyuspan.member.provider.service.impl;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.member.provider.dao.MemberDao;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:12
 */
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public List<Member> getMembers() {
        return memberDao.findAll();
    }

    @Override
    public Member save(Member member) {
        return memberDao.save(member);
    }
}
