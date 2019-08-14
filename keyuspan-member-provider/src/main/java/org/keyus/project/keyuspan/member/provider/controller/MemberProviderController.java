package org.keyus.project.keyuspan.member.provider.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-16  下午10:17
 */
@RestController
@AllArgsConstructor
public class MemberProviderController {

    private final MemberService memberService;

    @PostMapping("/members")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @PostMapping("/save_or_update")
    public Member saveMember(@RequestBody Member member) {
        return memberService.saveMember(member);
    }

    @PostMapping("/find_one")
    public Member findOne(@RequestBody Member member) {
        return memberService.findOne(member);
    }

    @PostMapping("/get_member_id_list")
    public List<Long> getMemberIdList () {
        return memberService.getMemberIdList();
    }
}
