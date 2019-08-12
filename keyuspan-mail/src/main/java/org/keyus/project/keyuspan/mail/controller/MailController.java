package org.keyus.project.keyuspan.mail.controller;

import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.mail.service.MailService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keyus
 * @create 2019-07-17  下午11:15
 */
@RestController
@AllArgsConstructor
public class MailController {

    private final MailService mailService;
}
