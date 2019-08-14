package org.keyus.project.keyuspan.api.client.service.member;

import org.keyus.project.keyuspan.api.po.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author keyus
 * @create 2019-07-29  下午2:25
 */
@FeignClient(value = "keyuspan-member-provider")
public interface MemberClientService {

    @PostMapping("/members")
    List<Member> getMembers();

    @PostMapping("/save_or_update")
    Member saveMember(@RequestBody Member member);

    @PostMapping("/find_one")
    Member findOne(@RequestBody Member member);

    @PostMapping("/get_member_id_list")
    List<Long> getMemberIdList ();
}
