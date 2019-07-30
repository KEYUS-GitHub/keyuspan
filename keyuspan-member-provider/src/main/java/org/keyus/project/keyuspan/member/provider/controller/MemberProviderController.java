package org.keyus.project.keyuspan.member.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-16  下午10:17
 */
@RestController
@AllArgsConstructor
public class MemberProviderController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ServerResponse <List<Member>> getMembers() {
        return ServerResponse.createBySuccessWithData(memberService.getMembers());
    }

    @PostMapping("/save")
    public ServerResponse <Member> saveMember(@RequestBody Member member) {
        Member save = memberService.save(member);
        // 如果ID不是空值，则表明保存成功
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.MEMBER_REGISTER_FAIL.getMessage());
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @PostMapping("/find_one")
    public ServerResponse <Member> findOne(@RequestBody Member member) {
        Optional<Member> optional = memberService.findOne(Example.of(member));
        // 判断是否存在这样一条数据库中的记录
        return optional.map(ServerResponse::createBySuccessWithData).orElseGet(ServerResponse::createBySuccessNullValue);
    }
}
