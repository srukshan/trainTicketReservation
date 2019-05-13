package com.smartimpulse.trainapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService{
	
	private JavaMailSender mailSender;
	@Value("${custom.mail}")
    private String email;
	
	@Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
	
	public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(email);
        mailSender.send(mailMessage);
    }
}
