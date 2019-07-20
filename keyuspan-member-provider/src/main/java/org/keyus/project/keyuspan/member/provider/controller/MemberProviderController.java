package org.keyus.project.keyuspan.member.provider.controller;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

/**
 * @author keyus
 * @create 2019-07-16  下午10:17
 */
@RestController
public class MemberProviderController {

    private final MemberService memberService;

    public MemberProviderController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ServerResponse getMembers() {
        return ServerResponse.createBySuccessWithData(memberService.getMembers());
    }

    @PostMapping("/save")
    public ServerResponse saveMember(@RequestBody Member member) {
        Member save = memberService.save(member);
        // 如果ID不是空值，则表明保存成功
        if (Objects.isNull(save.getId())) {
            return ServerResponse.createByErrorWithMessage("保存失败!");
        } else {
            return ServerResponse.createBySuccessWithData(save);
        }
    }

    @PostMapping("/find_one")
    public ServerResponse findOne(@RequestBody Member member) {
        Optional<Member> optional = memberService.findOne(Example.of(member));
        // 判断是否存在这样一条数据库中的记录
        if (optional.isPresent()) {
            return ServerResponse.createBySuccessWithData(optional.get());
        } else {
            return ServerResponse.createBySuccessNullValue();
        }
    }
}
