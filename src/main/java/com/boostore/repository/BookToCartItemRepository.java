package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.boostore.domain.BookToCartItem;
import com.boostore.domain.CartItem;

@Transactional
public interface BookToCartItemRepository extends  CrudRepository<BookToCartItem, Long>{

	void deleteByCartItem(CartItem cartItem);

}
