package org.keyus.project.keyuspan.member.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.service.common.CommonClientService;
import org.keyus.project.keyuspan.api.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VerificationCode;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-07-17  下午6:22
 */
@RestController
@AllArgsConstructor
public class MemberConsumerController {

    private final CommonClientService commonClientService;

    private final MemberClientService memberClientService;

    @GetMapping("/members")
    public ServerResponse getMembers() {
        return memberClientService.getMembers();
    }

    @PostMapping("/register")
    public ServerResponse register(Member member) {
        return memberClientService.saveMember(member);
    }

    @PostMapping("/login")
    public ServerResponse login(HttpSession session, Member member, @RequestParam("key") String key, @RequestParam("answer") String answer) {
        // 校验验证码是否正确
        ServerResponse response = commonClientService.checkVerificationCode(VerificationCode.create(key, answer));
        // 如果验证码未校验成功，
        if (ServerResponse.isError(response)) {
            return response;
        }

        // 否则，查找用户名与密码匹配的记录
        response = memberClientService.findOne(member);
        if (ServerResponse.isSuccess(response)) {
            // 用户名或密码错误，查无此记录，故而登录失败
            if (ServerResponse.isNullValue(response)) {
                return ServerResponse.createByErrorWithMessage("用户名或密码错误");
            } else {
                // 登录成功，登录信息存入session当中
                session.setAttribute("member", response.getData());
                return response;
            }
        } else {
            return ServerResponse.createByErrorWithMessage("系统出现异常，请稍后再试");
        }
    }
}
