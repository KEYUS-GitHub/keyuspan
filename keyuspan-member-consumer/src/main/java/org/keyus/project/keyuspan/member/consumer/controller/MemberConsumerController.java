package org.keyus.project.keyuspan.member.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.vo.MemberVO;
import org.keyus.project.keyuspan.member.consumer.service.MemberConsumerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        ServerResponse <List<Member>> serverResponse = memberConsumerService.getMembers();
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(MemberVO.getInstances(serverResponse.getData()));
        }
        return serverResponse;
    }

    @PostMapping("/register")
    public ServerResponse register(Member member, @RequestParam("key") String key, HttpSession session) {
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_IMAGE.getName());
        ServerResponse<Member> serverResponse = memberConsumerService.register(member, key, capText);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(MemberVO.getInstance(serverResponse.getData()));
        }
        return serverResponse;
    }

    @PostMapping("/login")
    public ServerResponse login(HttpSession session, Member member, @RequestParam("key") String key) {
        ServerResponse<Member> serverResponse = memberConsumerService.login(session, member, key);
        if (ServerResponse.isSuccess(serverResponse)) {
            return ServerResponse.createBySuccessWithData(MemberVO.getInstance(serverResponse.getData()));
        }
        return serverResponse;
    }
}
