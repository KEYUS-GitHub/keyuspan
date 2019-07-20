package org.keyus.project.keyuspan.member.consumer.controller;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.keyus.project.keyuspan.api.util.VerificationCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-07-17  下午6:22
 */
@RestController
public class MemberConsumerController {

    private static final String COMMON_REST_URL_PREFIX = "http://keyuspan-common-provider";
    private static final String MEMBER_REST_URL_PREFIX = "http://keyuspan-member-provider";

    private final RestTemplate restTemplate;

    public MemberConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/members")
    public ServerResponse getMembers() {
        return restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/members", null, ServerResponse.class);
    }

    @PostMapping("/register")
    public ServerResponse register(Member member) {
        return restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/save", member, ServerResponse.class);
    }

    @PostMapping("/login")
    public ServerResponse login(HttpSession session, Member member, @RequestParam("key") String key, @RequestParam("answer") String answer) {
        // 校验验证码是否正确
        ServerResponse response = restTemplate.postForObject(COMMON_REST_URL_PREFIX + "/check_verification_code",
                VerificationCode.create(key, answer), ServerResponse.class);
        // 如果验证码未校验成功，
        if (ServerResponse.isError(response)) {
            return response;
        }

        // 否则，查找用户名与密码匹配的记录
        response = restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/find_one", member, ServerResponse.class);
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
