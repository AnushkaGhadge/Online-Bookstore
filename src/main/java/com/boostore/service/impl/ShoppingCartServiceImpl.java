 package com.boostore.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boostore.domain.CartItem;
import com.boostore.domain.ShoppingCart;
import com.boostore.repository.CartItemRepository;
import com.boostore.repository.ShoppingCartRepository;
import com.boostore.service.CartItemService;
import com.boostore.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Override
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart); 
		
		for(CartItem cartItem : cartItemList) {
			if(cartItem.getBook().getInStockNumber()>0) {
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubTotal());
			}
		}
		
		shoppingCart.setGrandTotal(cartTotal);
		
		shoppingCartRepository.save(shoppingCart);
		
		return shoppingCart;
	}

	@Override
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		for(CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemRepository.save(cartItem);
		}
		shoppingCart.setGrandTotal(new BigDecimal(0));
		
		shoppingCartRepository.save(shoppingCart);
	}

}
