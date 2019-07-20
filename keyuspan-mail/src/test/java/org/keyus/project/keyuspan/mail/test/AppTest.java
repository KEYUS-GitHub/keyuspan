package org.keyus.project.keyuspan.mail.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyus.project.keyuspan.KeyuspanMailServer;
import org.keyus.project.keyuspan.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author keyus
 * @create 2019-07-17  下午11:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KeyuspanMailServer.class)
public class AppTest {

    @Autowired
    private MailService mailService;

    @Test
    public void test1() {
        mailService.sendSimpleMail("542300786@qq.com", "这是一个测试邮件", "这是一个测试邮件");
    }
}
