package org.keyus.project.keyuspan.mail.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author keyus
 * @create 2019-07-17  下午11:18
 */
@Slf4j
@Service
public class MailService {

    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String formMail;

    public MailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendSimpleMail(String toMail, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(formMail);
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        try {
            sender.send(simpleMailMessage);
            log.info("发送给" + toMail + "简单邮件已经发送。 subject：" + subject);
        } catch (Exception e){
            log.error("发送给" + toMail + "send mail error subject：" + subject);
            e.printStackTrace();
        }
    }

    public void sendHtmlMail(String toMail, String subject, String content) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setFrom(formMail);
            mimeMessageHelper.setText(content,true);
            mimeMessageHelper.setSubject(subject);
            sender.send(mimeMessage);
            log.info("发送给" + toMail + "html邮件已经发送。 subject：" + subject);
        } catch (MessagingException e) {
            log.error("发送给" + toMail + "html send mail error subject：" + subject);
            e.printStackTrace();
        }
    }

    public void sendAttachmentsMail(String toMail, String subject, String content, String filePath) {
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(formMail);
            helper.setTo(toMail);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf("/"));
            helper.addAttachment(fileName, file);
            sender.send(message);
            log.info("发送给" + toMail + "带附件的邮件已经发送。");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("发送给" + toMail + "带附件的邮件时发生异常！", e);
        }
    }
}
