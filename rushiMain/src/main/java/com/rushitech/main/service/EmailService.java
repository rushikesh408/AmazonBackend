package com.rushitech.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String fromEmail, String toEmail, String mailSubject, String mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(mailSubject);
        message.setText(mailBody);
        javaMailSender.send(message);
    }

    public void sendTemplateEmail(
            String fromEmail,
            String toEmail,
            String mailSubject,
            String fileName,
            String userName,
            String emailType,
            String actionUrl
    ) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("emailType", emailType);
        context.setVariable("actionUrl", actionUrl);

        if ("reset-password".equals(emailType)) {
            context.setVariable("actionText", "Reset Password");
        } else {
            context.setVariable("actionText", "Get Started");
        }

        String htmlContent = templateEngine.process(fileName, context);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(mailSubject);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}