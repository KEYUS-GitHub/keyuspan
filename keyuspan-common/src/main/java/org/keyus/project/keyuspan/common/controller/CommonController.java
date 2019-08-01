package org.keyus.project.keyuspan.common.controller;

import com.google.code.kaptcha.Producer;
import lombok.AllArgsConstructor;
import org.keyus.project.keyuspan.api.enums.SessionAttributeNameEnum;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author keyus
 * @create 2019-07-18  下午9:43
 */
@RestController
@AllArgsConstructor
public class CommonController {

    // 验证码生成器
    private final Producer captchaProducer;

    /**
     * 仅仅将文本存入session，不输出
     */
    @GetMapping("/create_text")
    public void createCapTextForMail (HttpSession session) {
        String capText = captchaProducer.createText();
        session.setAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_TEXT.getName(), capText);
    }

    /**
     * 输出一张带验证码的图片，将文本存入session
     */
    @GetMapping("/get_image_for_output")
    public void getImageForOutput (HttpServletResponse response, HttpSession session) throws IOException {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // 生成验证码文本
        String capText = captchaProducer.createText();
        // 将验证码存到session
        session.setAttribute(SessionAttributeNameEnum.CAPTCHA_FOR_IMAGE.getName(), capText);
        // create the image with the text
        BufferedImage image = captchaProducer.createImage(capText);
        // write the data out
        try (ServletOutputStream output = response.getOutputStream()) {
            ImageIO.write(image, "jpg", output);
            output.flush();
        }
    }
}
