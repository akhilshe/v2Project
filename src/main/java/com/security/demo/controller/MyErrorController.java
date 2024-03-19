package com.security.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class MyErrorController {

	@RequestMapping("/error")
	public String handle(HttpServletRequest request) {
		int status = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status == 404)
			return "404";
		else
			return "error";

	}
}
