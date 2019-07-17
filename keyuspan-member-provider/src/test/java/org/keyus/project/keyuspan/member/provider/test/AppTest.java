package org.keyus.project.keyuspan.member.provider.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyus.project.keyuspan.api.pojo.Member;
import org.keyus.project.keyuspan.member.provider.dao.MemberDao;
import org.keyus.project.keyuspan.member.provider.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author keyus
 * @create 2019-07-17  下午5:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private MemberDao memberDao;

    @Test
    public void test1() {
        List<Member> all = memberDao.findAll();
        System.out.println(all);
    }

    @Test
    public void test2() {
        List<Member> list = new ArrayList<>(50);
        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            member.setUsername(UUID.randomUUID().toString().substring(0, 6));
            member.setHeadImgUri("/");
            list.add(member);
        }
        memberDao.saveAll(list);
    }
}
