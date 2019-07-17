package org.keyus.project.keyuspan.member.provider.controller;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:17
 */
@RestController
@RequestMapping("/member")
public class MemberProviderController {

    private final MemberService memberService;

    public MemberProviderController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }
}
