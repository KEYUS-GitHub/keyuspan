package org.keyus.project.keyuspan.member.provider.service.impl;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.member.provider.dao.MemberDao;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public <S extends Member> S save(S entity) {
        return memberDao.save(entity);
    }

    @Override
    public <S extends Member> Optional<S> findOne(Example<S> example) {
        return memberDao.findOne(example);
    }
}
