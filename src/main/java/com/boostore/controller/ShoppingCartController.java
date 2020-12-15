package com.boostore.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boostore.domain.Book;
import com.boostore.domain.CartItem;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;
import com.boostore.service.BookService;
import com.boostore.service.CartItemService;
import com.boostore.service.ShoppingCartService;
import com.boostore.service.UserService;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		shoppingCartService.updateShoppingCart(shoppingCart);
		
		model.addAttribute("cartItemList",cartItemList);
		model.addAttribute("shoppingCart",shoppingCart);
		return "shoppingCart";
	}
	
	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("book") Book book,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
			) {
		User user = userService.findByUsername(principal.getName());
		book = bookService.findOne(book.getId());
		
		if(Integer.parseInt(qty)>book.getInStockNumber()) {
			model.addAttribute("notEnoughStock",true);
			return "forward:/bookDetail?id="+book.getId();
		}
		
		CartItem cartItem = cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));
		model.addAttribute("addBookSuccess",true);
		
		
		return "forward:/bookDetail?id="+book.getId();
	}
	
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty
			) {
		CartItem cartItem = cartItemService.findById(cartItemId);
		cartItem.setQty(qty);
		
		cartItemService.updateCartItem(cartItem);
		
		return "forward:/shoppingCart/cart";
		
	}
	
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id) {
		cartItemService.removeCartItem(cartItemService.findById(id));
		return "forward:/shoppingCart/cart";
	}
}