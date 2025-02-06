package com.writely.auth.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
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
            MailType mailType, String mailTo, Context variables
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(mailTo);
        mimeMessageHelper.setSubject(mailType.getSubject());
        mimeMessageHelper.setText(templateEngine.process(mailType.getTemplatePath(), variables), true);

        javaMailSender.send(mimeMessage);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MailType {
        JOIN("[WritelyForWriters] 회원가입 안내입니다.", "mail/join"),
        CHANGE_PASSWORD("[WritelyForWriters] 비밀번호 변경 안내입니다.", "mail/change-password");

        private final String subject;
        private final String templatePath;
    }
}
