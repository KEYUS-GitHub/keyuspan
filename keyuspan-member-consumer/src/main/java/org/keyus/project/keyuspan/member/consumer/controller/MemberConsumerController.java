package org.keyus.project.keyuspan.member.consumer.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.ErrorMessageEnum;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.client.service.member.MemberClientService;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author keyus
 * @create 2019-07-17  下午6:22
 */
@RestController
@AllArgsConstructor
public class MemberConsumerController {

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
    public ServerResponse login(HttpSession session, Member member, @RequestParam("key") String key) {

        // 获取并校验验证码的值
        String capText = (String) session.getAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_IMAGE.getName());
        if (!Objects.equals(key, capText)) {
            return ServerResponse.createByErrorWithMessage(ErrorMessageEnum.CAPTCHA_CHECK_ERROR.getMessage());
        }

        // 校验成功，则查找用户名与密码匹配的记录
        ServerResponse <Member> response = memberClientService.findOne(member);
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
