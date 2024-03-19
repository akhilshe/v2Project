package com.security.demo.service.impl;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import com.security.demo.dao.UserDao;
import com.security.demo.dto.User;
import com.security.demo.helper.Mailhelper;

import jakarta.servlet.http.HttpSession;



@Service
public  class Services {

	@Autowired
	UserDao userDao;
	@Autowired
	Mailhelper mailhelper;
	
	public String signup(User user, BindingResult result) {
		if (userDao.checkEmailDuplicate(user.getEmail()))
			result.rejectValue("email", "error.email", "Account Already Exists - Check Email");
		if (userDao.checkMobileDuplicate(user.getMobile()))
			result.rejectValue("mobile", "error.mobile", "Account Already Exists - Check Mobile");
	
		if (result.hasErrors()) {
			return "Signup";
		}
		else
		{
			user.setUser("user");
//			user.setPassword(AES.encrypt(user.getPassword(), "123"));
			user.setOtp(new Random().nextInt(100000, 999999));
			mailhelper.sendotp(user);
			userDao.createUser(user);
//			
			return "redirect:/send-otp/" + user.getId();
			
		}
	}
	public  String login(String email, String password, HttpSession session) {
		
		 User user = userDao.findbymail(email);
		 if (user == null)
		 {
			 session.setAttribute("failMessage","invalid email");
			 return "Signin";
		 }
		 else 
		 {
			 if(user.getPassword().equals(password))
			 {
				 session.setAttribute("user",user);
				 session.setAttribute("SuccessMessage","login sucessfull");
				 return "redirect:/";
			 }
			 else 
			 {
				 session.setAttribute("failMessage","invalid Password");
				 return "Signin";
			 }
		 }		 
	}
	public String verifyotp(int id, int otp, ModelMap modelMap, HttpSession session) {
		// TODO Auto-generated method stub
		
		User user = userDao.findbyid(id);
		int otp_from_database = user.getOtp();
		if (otp == otp_from_database)
		{
			
			user.setVerified(true);
			session.setAttribute("SuccessMessage", "OTP verified sucessfully");
			
			return "redirect:/signin";
		}
		else
		{	System.out.println("not verified");
			session.setAttribute("failMessage", "OTP verification failed");
			return "OtpEnter";
		}
		
	}

	


	
	
}
