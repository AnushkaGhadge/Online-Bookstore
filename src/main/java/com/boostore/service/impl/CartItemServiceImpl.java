package com.boostore.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boostore.domain.Book;
import com.boostore.domain.BookToCartItem;
import com.boostore.domain.CartItem;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;
import com.boostore.repository.BookToCartItemRepository;
import com.boostore.repository.CartItemRepository;
import com.boostore.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private BookToCartItemRepository bookToCartItemRepository;
	
	@Override
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		double price = cartItem.getBook().getOurPrice();
		BigDecimal dec1= BigDecimal.valueOf(price);
		
		BigDecimal bigDecimal = dec1.multiply(new BigDecimal(cartItem.getQty()));
		
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		cartItem.setSubTotal(bigDecimal);
		
		cartItemRepository.save(cartItem);
		
		return cartItem;
	}

	@Override
	public CartItem addBookToCartItem(Book book, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());
		for(CartItem cartItem : cartItemList) {
			if(book.getId()==cartItem.getBook().getId()) {
				cartItem.setQty(cartItem.getQty()+qty);
				BigDecimal ourPrice = BigDecimal.valueOf(book.getOurPrice());
				BigDecimal qtyy = BigDecimal.valueOf(qty);
				cartItem.setSubTotal(ourPrice.multiply(qtyy));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setBook(book);
		cartItem.setQty(qty);
		BigDecimal ourPrice = BigDecimal.valueOf(book.getOurPrice());
		BigDecimal qtyy = BigDecimal.valueOf(qty);
		cartItem.setSubTotal(ourPrice.multiply(qtyy));
		
		cartItem = cartItemRepository.save(cartItem);
		
		BookToCartItem bookToCartItem = new BookToCartItem();
		bookToCartItem.setBook(book);
		bookToCartItem.setCartItem(cartItem);
		bookToCartItemRepository.save(bookToCartItem);
		return cartItem;
	}

	@Override
	public CartItem findById(Long cartItemId) {
		Optional<CartItem> cartItemResponse = cartItemRepository.findById(cartItemId);
		CartItem cartItem = cartItemResponse.get();
		return cartItem;
	}

	@Override
	public void removeCartItem(CartItem cartItem) {
		bookToCartItemRepository.deleteByCartItem(cartItem);
		cartItemRepository.delete(cartItem);
	}

}
