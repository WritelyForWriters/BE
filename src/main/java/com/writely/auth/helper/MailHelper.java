package com.writely.auth.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
            MailType mailType, String mailTo, MailData data
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(mailTo);
        mimeMessageHelper.setSubject(mailType.getSubject());

        Context variables = new Context();
        variables.setVariable("data", data);
        mimeMessageHelper.setText(templateEngine.process(mailType.getTemplatePath(), variables), true);

        javaMailSender.send(mimeMessage);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MailType {
        JOIN("[라이틀리] 계정을 인증해주세요!", "mail/join"),
        CHANGE_PASSWORD("[라이틀리] 비밀번호 재설정 안내", "mail/change-password");

        private final String subject;
        private final String templatePath;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class MailData {
        private String token;
        private String nickname;
    }
}
