package org.keyus.project.keyuspan.member.provider.service.impl;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.member.provider.dao.MemberDao;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-16  下午10:12
 */
@Service
@Transactional
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public ServerResponse<List<Member>> getMembers() {
        return ServerResponse.createBySuccessWithData(memberDao.findAll());
    }

    @Override
    public ServerResponse <Member> saveMember(Member member) {
        Member save = memberDao.save(member);
        // 如果ID不是空值，则表明保存成功
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_REGISTER_FAIL.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @Override
    public ServerResponse <Member> findOne(Member member) {
        Optional<Member> optional = memberDao.findOne(Example.of(member));
        // 判断是否存在这样一条数据库中的记录
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(() -> ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_NOT_EXIST.getMessage()));
    }

    @Override
    public ServerResponse <List<Long>> getMemberIdList () {
        List<Member> members = memberDao.findAll();
        List<Long> result = new ArrayList<>();
        for (Member member : members) {
            result.add(member.getId());
        }
        return ServerResponse.createBySuccessWithData(result);
    }
}
