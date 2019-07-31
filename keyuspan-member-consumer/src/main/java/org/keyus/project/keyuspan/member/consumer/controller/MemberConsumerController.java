package org.keyus.project.keyuspan.member.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.client.service.common.CommonClientService;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
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
        // TODO: 19-7-30 补充注册的逻辑，如验证码验证，用户名不允许重复之类的
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
                return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
            } else {
                // 登录成功，登录信息存入session当中
                session.setAttribute(SessionAttributeNameEnum.LOGIN_MEMBER.getName(), response.getData());
                return response;
            }
        } else {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.SYSTEM_EXCEPTION.getMessage());
        }
    }
}
