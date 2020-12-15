package com.boostore.service;

import com.boostore.domain.ShoppingCart;

public interface ShoppingCartService {
	
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);
}
