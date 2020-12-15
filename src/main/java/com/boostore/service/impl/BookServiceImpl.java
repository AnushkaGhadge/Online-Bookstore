package com.boostore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boostore.domain.Book;
import com.boostore.repository.BookRepository;
import com.boostore.service.BookService;
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}

	@Override
	public Book findOne(Integer id) {
		Optional<Book> bookResponse = bookRepository.findById(id);
		Book book = bookResponse.get();
		return book;
	}

}
