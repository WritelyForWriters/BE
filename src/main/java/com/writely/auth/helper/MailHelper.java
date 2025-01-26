package com.writely.auth.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class MailHelper {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void send (
            String mailTo, String subject, String templatePath, Context variables
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(mailTo);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(templateEngine.process(templatePath, variables), true);

        javaMailSender.send(mimeMessage);
    }
}
