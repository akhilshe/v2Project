package com.security.demo.helper;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.security.demo.dto.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Component
public class Mailhelper {

	@Autowired
	JavaMailSender mailSender;

	public  void sendotp(User user) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
		
		String gen = "";
		if (user.getGender().equals("male"))
			gen = "Mr. ";
		else
			gen = "Ms. ";
		

		String body = "<html><body><h1>Hello " + gen + user.getName() + ",</h1><h2>Your One Time Password is: " + user.getOtp() + "</h2><h3>Please do not disclose it with anyone</h3><h2>Thanks and Regards,</h2></body></html>";
		
		try {
			mimeMessageHelper.setFrom("akhil.m@testsigma.com","bookadda");
			mimeMessageHelper.setTo(user.getEmail());
			mimeMessageHelper.setSubject("Your OTP is ...................");
			mimeMessageHelper.setText(body,true);
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mailSender.send(message);
	}
	
	

}
