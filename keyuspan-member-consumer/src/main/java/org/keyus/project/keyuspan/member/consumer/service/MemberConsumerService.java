package org.keyus.project.keyuspan.member.consumer.service;

import org.keyus.project.keyuspan.api.po.Member;
import org.keyus.project.keyuspan.api.util.ServerResponse;

import javax.servlet.http.HttpSession;

/**
 * @author keyus
 * @create 2019-08-12  下午2:21
 */
public interface MemberConsumerService {

    ServerResponse getMembers();

    ServerResponse register(Member member, String key, String capText);

    ServerResponse login(HttpSession session, Member member, String key);
}
