package com.security.demo.service.impl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.security.demo.dao.BookDao;
import com.security.demo.dto.Books;
import com.security.demo.dto.User;

@Service
public class AdminService {
	@Autowired
	Books book;
	
	@Autowired
	BookDao bookdao;
	
	
	
	public String loadAdminDashBoard(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if (user ==null)
		{
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		}
		else 
		{
		if (user.getUser().equals("ADMIN"))
		{
			return "Admindashboard";
		}
		else 
		{
			session.setAttribute("failMessage", "You are Unauthorized to use this URL");
			return "redirect:/signin";
		}
		}
	}
	
	

	public String addbook(HttpSession session, ModelMap map)
	{
		User user = (User) session.getAttribute("user");
		if (user == null) {
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
		} else {
			if (user.getUser().equals("ADMIN")) {
				map.put("book", book);
				return "AddBook";
			} else {
				session.setAttribute("failMessage", "You are Unauthorized to use this URL");
				return "redirect:/";
			}
		}
}


	public String addbooks(HttpSession session,  Books book2, MultipartFile photo, MultipartFile pdf, BindingResult result) throws IOException {
		bookdao.saveBook(book2);
		byte[] pic = new byte[photo.getInputStream().available()];
		
		photo.getInputStream().read(pic);
		System.out.println(pic.length);
		
		byte[] pdffile = new byte[pdf.getInputStream().available()];
		pdf.getInputStream().read(pdffile);
		System.out.println(pdffile.length);
		book.setPicture(pic);
		book.setPdf(pdffile);
		
		session.setAttribute("SuccessMessage", "Book added sucessfully");
		return "redirect:/admin";
	}

	public String viewlist(HttpSession session, ModelMap map) {
		User user = (User) session.getAttribute("user");
	if (user == null) 
	{
			session.setAttribute("failMessage", "Session Expired");
			return "redirect:/signin";
	}	
		if (user.equals("ADMIN"))
		{
			List<Books> books = bookdao.fetchAllBooks();
			if(books.isEmpty())
			{
				session.setAttribute("failMessage", "No books added yet");
				return "redirect:/Admindashboard";
			}
			else
			{
				map.put("books", books);
				return "Booklist";
			}
			
		}
		else {
			session.setAttribute("failMessage", "You are Unauthorized to use this URL");
			return "redirect:/";
		}
	}



	public String editbook(HttpSession session, ModelMap map,int id ) {
		User user = (User)session.getAttribute("user");
		Books book = bookdao.findById(id);
		map.put("book", book);
		return "Editbook";
	}
	public String updatebooks(HttpSession session, Books book, MultipartFile photo, MultipartFile bookPdf, BindingResult result) throws IOException {
		bookdao.saveBook(book);
		byte[] pic = new byte[photo.getInputStream().available()];
		
		photo.getInputStream().read(pic);
		System.out.println(pic.length);
		
		byte[] pdffile = new byte[bookPdf.getInputStream().available()];
		bookPdf.getInputStream().read(pdffile);
		System.out.println(pdffile.length);
		book.setPicture(pic);
		book.setPdf(pdffile);
		
		session.setAttribute("successMessage", "Book added sucessfully");
		return "redirect:/admin";
	}
	



	public String deletebook(int id,HttpSession session) {
		bookdao.delete(id);
		session.setAttribute("successMessage", "Book Deleted Success");
		return "redirect:/admin/manage-books";
		
	}}
	
	

