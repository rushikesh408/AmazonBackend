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
	JavaMailSender javaMailSender;
	@Autowired
	TemplateEngine templateEngine;
	
	
	public void sendEmail(String fromEmail, String toEmail,String mailSubject, String mailBody) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject(mailSubject);
		message.setText(mailBody);
		javaMailSender.send(message);
		
	}
	
	public void sendTemplateEmail(String fromEmail, String toEmail, String mailSubject, String fileName) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		
		Context context = new Context();
		String htmlContent = templateEngine.process(fileName, context);
		
		
		// Set email properties
		helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(mailSubject);
        helper.setText(htmlContent, true); // Set true for HTML content

        // Send the email
        javaMailSender.send(mimeMessage);
		
		
	}
	
		
	

}
