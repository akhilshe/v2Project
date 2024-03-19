package com.security.demo.dao;

import java.awt.print.Book;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.security.demo.dto.Books;
import com.security.demo.repository.BookRepo;


@Repository
public class BookDao {
	@Autowired
	BookRepo bookRepository;

	@Autowired
//	BookOrderRepository bookOrderRepository;

	public void saveBook(Books book) {
		bookRepository.save(book);
	}

	public List<Books> fetchAllBooks() {
		return bookRepository.findAll();
	}

	public void delete(int id) {
		bookRepository.deleteById(id);
	}

	public Books findById(int id) {
		return bookRepository.findById(id).orElseThrow();
	}

	

//	public BookOrder findOrderById(int id) {
//		return bookOrderRepository.findById(id).orElseThrow();
//	}
//
//	public void saveBookOrder(BookOrder bookOrder) {
//		bookOrderRepository.save(bookOrder);
//	}
}
