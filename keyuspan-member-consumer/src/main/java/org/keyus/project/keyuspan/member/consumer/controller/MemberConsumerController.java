package org.keyus.project.keyuspan.member.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.member.consumer.service.MemberConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-07-17  下午6:22
 */
@RestController
@AllArgsConstructor
public class MemberConsumerController {

    private final MemberConsumerService memberConsumerService;

    @GetMapping("/members")
    public ServerResponse getMembers() {
        return memberConsumerService.getMembers();
    }

    @PostMapping("/register")
    public ServerResponse register(Member member, @RequestParam("key") String key, HttpSession session) {
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_IMAGE.getName());
        return memberConsumerService.register(member, key, capText);
    }

    @PostMapping("/login")
    public ServerResponse login(HttpSession session, Member member, @RequestParam("key") String key) {
        return memberConsumerService.login(session, member, key);
    }
}
