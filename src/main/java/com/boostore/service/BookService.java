package com.boostore.service;

import java.util.List;

import com.boostore.domain.Book;

public interface BookService {

	List<Book> findAll();

	Book findOne(Integer id);
}
