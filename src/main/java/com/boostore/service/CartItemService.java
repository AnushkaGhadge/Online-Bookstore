package com.boostore.service;

import java.util.List;

import com.boostore.domain.Book;
import com.boostore.domain.CartItem;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	CartItem updateCartItem(CartItem cartItem);

	CartItem addBookToCartItem(Book book, User user, int parseInt);

	CartItem findById(Long cartItemId);

	void removeCartItem(CartItem findById);

}
