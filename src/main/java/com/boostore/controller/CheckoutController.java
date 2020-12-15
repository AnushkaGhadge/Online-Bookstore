package com.boostore.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.boostore.domain.BillingAddress;
import com.boostore.domain.CartItem;
import com.boostore.domain.Order;
import com.boostore.domain.Payment;
import com.boostore.domain.ShippingAddress;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;
import com.boostore.domain.UserBilling;
import com.boostore.domain.UserPayment;
import com.boostore.domain.UserShipping;
import com.boostore.service.BillingAddressService;
import com.boostore.service.CartItemService;
import com.boostore.service.OrderService;
import com.boostore.service.PaymentService;
import com.boostore.service.ShippingAddressService;
import com.boostore.service.ShoppingCartService;
import com.boostore.service.UserPaymentService;
import com.boostore.service.UserService;
import com.boostore.service.UserShippingService;
import com.boostore.utility.INDConstants;
import com.boostore.utility.MailContructor;

@Controller
public class CheckoutController {
	
	public ShippingAddress shippingAddress = new ShippingAddress();
	public BillingAddress billingAddress = new BillingAddress();
	public Payment payment = new Payment();
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailContructor mailConstructor;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private BillingAddressService billingAddressService;
	
	@Autowired
	private UserShippingService userShippingService;
	
	@Autowired
	private UserPaymentService userPaymentService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/checkout")
	public String checkout(
			@RequestParam("id")Long cartId,
			@RequestParam(value = "missingRequiredField", required=false) boolean missingRequiredField,
			Model model, Principal principal
			) {
		System.out.print("~~~~~~~~~~~~~~~~~~inside checkout~~~~~~~~~~~~~~~~~~~~~");
		User user = userService.findByUsername(principal.getName());
		
		if(cartId != user.getShoppingCart().getId()) {
			return "badRequestPage";
		}
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());
		if(cartItemList.size()==0) {
			model.addAttribute("emptyCart", true);
			return "forward:/shoppingCart/cart";
		}
		
		for(CartItem cartItem : cartItemList) {
			if(cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
				model.addAttribute("notEnoughStock",true);
				return "forward:/shoppingCart/cart";
			}
		}
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		List<UserPayment> userPaymentList = user.getUserPaymentList();
		
		model.addAttribute("userShippingList",userShippingList);
		model.addAttribute("userPaymentList",userPaymentList);
		
		if(userPaymentList.size()==0) {
			model.addAttribute("emptyPaymentList",true);
		}else {
			model.addAttribute("emptyPaymentList",false);
		}
		
		if(userShippingList.size()==0) {
			model.addAttribute("emptyShippingList",true);
		}else {
			model.addAttribute("emptyShippingList",false);
		}
		
		ShoppingCart shoppingCart = user.getShoppingCart();
		
		for(UserShipping userShipping : userShippingList) {
			if(userShipping.isUserShippingDefault()) {
				shippingAddressService.setByUserShipping(userShipping, shippingAddress);
			}
		}
		
		for(UserPayment userPayment : userPaymentList) {
			if(userPayment.isDeafaultPayment()) {
				paymentService.setByUserPayment(userPayment, payment);
				billingAddressService.setByUserBilling(billingAddress, userPayment.getUserBilling());
			}
		}
		System.out.println(shippingAddress.toString());
		System.out.println(billingAddress.toString());
		System.out.println(payment.toString());
		
		
		model.addAttribute("shippingAddress", shippingAddress);
		model.addAttribute("payment", payment);
		model.addAttribute("billingAddress", billingAddress);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", user.getShoppingCart());
		
		List<String> stateList = INDConstants.listOfINDStatesCodes;
		Collections.sort(stateList);
		model.addAttribute("stateList", stateList);
		
		model.addAttribute("classActiveShipping",true);
		
		if(missingRequiredField) {
			model.addAttribute("missingRequiredField",true);
			
		}
		return "checkout";
	}
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public String checkoutPost(
			@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
			@ModelAttribute("billingAddress") BillingAddress billingAddress,
			@ModelAttribute("payment") Payment payment,
			@ModelAttribute("shippingMethod") String shippingMethod,
			@ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
			Principal principal,
			Model model
			) {
		ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		model.addAttribute("cartItemList", cartItemList);
		if(billingSameAsShipping.equals("true")) {
			billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
			billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
			billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
			billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
			billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
			billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
			billingAddress.setBillingAddressZipCode(shippingAddress.getShippingAddressZipCode());
		}
		
		if(shippingAddress.getShippingAddressStreet1().isEmpty() ||
				shippingAddress.getShippingAddressCity().isEmpty() || 
				shippingAddress.getShippingAddressState().isEmpty() ||
				shippingAddress.getShippingAddressName().isEmpty() || 
				shippingAddress.getShippingAddressZipCode().isEmpty() ||
				payment.getCardNumber().isEmpty() || payment.getCvv()==0 ||
				billingAddress.getBillingAddressCity().isEmpty() ||
				billingAddress.getBillingAddressStreet1().isEmpty() ||
				billingAddress.getBillingAddressState().isEmpty() ||
				billingAddress.getBillingAddressName().isEmpty() ||
				billingAddress.getBillingAddressZipCode().isEmpty() 
				) {
			return "redirect:/checkout?id="+shoppingCart.getId()+"&missingRequiredField=true";
		}
		
		User user = userService.findByUsername(principal.getName());
		
		Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);
		
		mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));
		
		shoppingCartService.clearShoppingCart(shoppingCart);
		
		LocalDate today = LocalDate.now();
		
		LocalDate estimatedDeliveryDate;
		
		if(shippingMethod.equals("groundShipping")) {
			estimatedDeliveryDate = today.plusDays(5);
		}else {
			estimatedDeliveryDate = today.plusDays(2);
		}
		
		model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);
		
		return "orderSubmittedPage";
	}
	
	@RequestMapping("/setShippingAddress")
	public String setShippingAddress(
			@RequestParam("userShippingId")Long userShippingId,
			Principal principal, Model model
			) {
		User user = userService.findByUsername(principal.getName());
		UserShipping userShipping = userShippingService.findById(userShippingId);
		
		if(userShipping.getUser().getId() != user.getId()) {
			return "badRequestPage";
		}else {
			shippingAddressService.setByUserShipping(userShipping, shippingAddress);

			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			/*
			 * BillingAddress billingAddress = new BillingAddress();
			 */			
			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());
			
			List<String> stateList = INDConstants.listOfINDStatesCodes;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();
			
			model.addAttribute("userShippingList",userShippingList);
			model.addAttribute("userPaymentList",userPaymentList);
			
			model.addAttribute("shippingAddress",shippingAddress);
			
			model.addAttribute("classActiveShipping", true);
			
			if(userPaymentList.size()==0) {
				model.addAttribute("emptyPaymentList",true);
			}else {
				model.addAttribute("emptyPaymentList",false);
			}
			
			
			model.addAttribute("emptyShippingList",false);
			
			
			return "checkout";
			
		}
		
	}
	
	@RequestMapping("/setPaymentMethod")
	public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId, Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		UserPayment userPayment =  userPaymentService.findById(userPaymentId);
		UserBilling userBilling = userPayment.getUserBilling();
		
		if(userPayment.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {
			paymentService.setByUserPayment(userPayment, payment);
			
			List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

			billingAddressService.setByUserBilling(billingAddress, userBilling);
			
			model.addAttribute("shippingAddress", shippingAddress);
			model.addAttribute("payment", payment);
			model.addAttribute("billingAddress", billingAddress);
			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("shoppingCart", user.getShoppingCart());
			
			List<String> stateList = INDConstants.listOfINDStatesCodes;
			Collections.sort(stateList);
			model.addAttribute("stateList", stateList);
			
			List<UserShipping> userShippingList = user.getUserShippingList();
			List<UserPayment> userPaymentList = user.getUserPaymentList();
			
			model.addAttribute("userShippingList",userShippingList);
			model.addAttribute("userPaymentList",userPaymentList);
			
			model.addAttribute("shippingAddress",shippingAddress);
			
			model.addAttribute("classActivePayment", true);
			
			model.addAttribute("emptyPaymentList",false);
			
			if(userShippingList.size()==0) {
				model.addAttribute("emptyShippingList",true);
			}else {
				model.addAttribute("emptyShippingList",false);
			}
			
			return "checkout";
		}
		
	}
}
