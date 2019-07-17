package org.keyus.project.keyuspan.member.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    public List getMembers() {
        return restTemplate.getForObject(MEMBER_REST_URL_PREFIX + "/member/members", List.class);
    }
}
