package com.security.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.security.demo.dto.Books;
import com.security.demo.service.impl.AdminService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/admin")
@MultipartConfig
public class AdminController {
	@Autowired
	AdminService adminService;
	@GetMapping
	public String loadAdminDashboard(HttpSession session) {
		return adminService.loadAdminDashBoard(session);
	}
	@GetMapping("/add-book")
	public String addBook(HttpSession session, ModelMap map) {
		return adminService.addbook(session, map);
	}
	
	@PostMapping("/add-book")
	public String addBooksp1(@Valid Books book, BindingResult result, @RequestParam MultipartFile photo,
			@RequestParam MultipartFile bookPdf, HttpSession session) throws IOException {
		if (result.hasErrors())
			return "AddBook";
		else
			return adminService.addbooks(session, book, photo, bookPdf, result);
	}
	
	
@GetMapping("/manage-books")
 	public String managebook(HttpSession session,ModelMap map){
		
	return adminService.viewlist(session,map);
	
}
@GetMapping("/edit/{id}")
public String editbook(@PathVariable int id ,HttpSession session, ModelMap map) {
	return adminService.editbook(session,map,id);
}
@GetMapping("/delete/{id}")
public String deletebook(@PathVariable int id ,HttpSession session) {
	return adminService.deletebook(id,session);
}
@PostMapping("/update-book")
public String updatebook(@Valid Books book,BindingResult result,@RequestParam MultipartFile picture,@RequestParam MultipartFile Pdf,HttpSession session) throws IOException 
{
	if (result.hasErrors())
		return "EditBook";
	else
		return adminService.updatebooks(session,book,picture,Pdf,result);
}
}
