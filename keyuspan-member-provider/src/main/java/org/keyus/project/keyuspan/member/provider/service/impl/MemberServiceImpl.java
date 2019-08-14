package org.keyus.project.keyuspan.member.provider.service.impl;

import com.codingapi.tx.annotation.TxTransaction;
import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.member.provider.dao.MemberDao;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-16  下午10:12
 */
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public List<Member> getMembers() {
        return memberDao.findAll();
    }

    @TxTransaction
    @Transactional
    @Override
    public Member saveMember(Member member) {
        return memberDao.save(member);
    }

    @Override
    public Member findOne(Member member) {
        Optional<Member> optional = memberDao.findOne(Example.of(member));
        // 判断是否存在这样一条数据库中的记录
        return optional.orElseGet(Member::new);
    }

    @Override
    public List<Long> getMemberIdList () {
        List<Member> members = memberDao.findAll();
        List<Long> result = new ArrayList<>();
        for (Member member : members) {
            result.add(member.getId());
        }
        return result;
    }
}
