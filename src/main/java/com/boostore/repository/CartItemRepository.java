package com.boostore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.boostore.domain.CartItem;
import com.boostore.domain.ShoppingCart;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
