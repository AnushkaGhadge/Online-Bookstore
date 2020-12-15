package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
