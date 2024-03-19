package com.security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.security.demo.dto.User;
import com.security.demo.service.impl.Services;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class Bookcontroller {
	@Autowired
	Services services;
	@Autowired
	User user;
	
@GetMapping("/")
 public String home() {
	return "Home.html";
}

@PostMapping("/login")
public String login(@RequestParam String email,@RequestParam String password,HttpSession session) {
	return services.login(email,password,session);
}

@GetMapping("/signup")
public String loadSignup(ModelMap map) {
	map.put("user", user);
	return "Signup";
}

@PostMapping("/signup")
public String signup(@Valid User user, BindingResult result,ModelMap map) {
	if (result.hasErrors()) {
		
		return "Signup";
	}
	else
		
			return services.signup(user, result);
		
}
//@PostMapping("/Login")
//public String Login(@RequestParam String email,@RequestParam String password,HttpSession session)
//{
//	return Services.login(email, password, session);
//}
//pathvariable

@GetMapping("/send-otp/{id}")
public String sendotp(@PathVariable int id,ModelMap map)
{
	map.put("id", id);
	map.put("sucess message","otp sucess sent");
	return "OtpEnter";	
}

@GetMapping("/signin")
public String loadSignin() {
	return "Signin";
}

@PostMapping("/verify-otp")
public String verifyotp(@RequestParam int id,@RequestParam int otp,HttpSession httpSession,ModelMap modelMap)
{
	return services.verifyotp(id,otp,modelMap,httpSession);	
}
@GetMapping("/logout")
public String logout(HttpSession session) {
	session.removeAttribute("user");
	session.setAttribute("SuccessMessage", "Logout Success");
	return "redirect:/";
}
}
