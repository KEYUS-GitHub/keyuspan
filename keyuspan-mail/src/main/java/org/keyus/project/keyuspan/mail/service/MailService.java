package org.keyus.project.keyuspan.mail.service;

/**
 * @author keyus
 * @create 2019-08-12  下午2:16
 */
public interface MailService {

    void sendSimpleMail(String toMail, String subject, String content);

    void sendHtmlMail(String toMail, String subject, String content);

    void sendAttachmentsMail(String toMail, String subject, String content, String filePath);
}
