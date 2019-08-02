package org.keyus.project.keyuspan.api.client.service.member;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午2:25
 */
@FeignClient(value = "keyuspan-member-provider", fallbackFactory = MemberClientServiceFallbackFactory.class)
public interface MemberClientService {

    @PostMapping("/members")
    ServerResponse <List<Member>> getMembers();

    @PostMapping("/save_or_update")
    ServerResponse <Member> saveMember(@RequestBody Member member);

    @PostMapping("/find_one")
    ServerResponse<Member> findOne(@RequestBody Member member);
}
