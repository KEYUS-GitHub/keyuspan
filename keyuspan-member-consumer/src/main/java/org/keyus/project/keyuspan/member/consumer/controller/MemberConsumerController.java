package org.keyus.project.keyuspan.member.consumer.controller;

import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-07-17  下午6:22
 */
@RestController
@RequestMapping("/consumer/member")
public class MemberConsumerController {

    private static final String MEMBER_REST_URL_PREFIX = "http://keyuspan-member-provider";

    private final RestTemplate restTemplate;

    public MemberConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/members")
    public ServerResponse getMembers() {
        return restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/member/members", null, ServerResponse.class);
    }

    @PostMapping("/register")
    public ServerResponse register(Member member) {
        return restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/member/save", member, ServerResponse.class);
    }

    @PostMapping("/login")
    public ServerResponse login(HttpSession session, Member member) {
        ServerResponse response = restTemplate.postForObject(MEMBER_REST_URL_PREFIX + "/member/find_one", member, ServerResponse.class);
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
