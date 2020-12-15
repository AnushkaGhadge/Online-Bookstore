package com.boostore.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boostore.domain.BillingAddress;
import com.boostore.domain.Book;
import com.boostore.domain.CartItem;
import com.boostore.domain.Order;
import com.boostore.domain.Payment;
import com.boostore.domain.ShippingAddress;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;
import com.boostore.repository.BillingAddressRepository;
import com.boostore.repository.OrderRepository;
import com.boostore.repository.PaymentRepository;
import com.boostore.repository.ShippingAddressRepository;
import com.boostore.service.CartItemService;
import com.boostore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	private BillingAddressRepository billingAddressRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;	
	
	@Autowired
	private CartItemService cartItemService;
	
	
	@Override
	@Transactional
	public synchronized Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Book book = cartItem.getBook();
			cartItem.setOrder(order);
			book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
		}
		
		order.setCartItemList(cartItemList);
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		shippingAddress.setId(order.getShippingAddress().getId());
		billingAddress.setId(order.getBillingAddress().getId());
		payment.setId(order.getPayment().getId());
		shippingAddressRepository.save(shippingAddress);
		billingAddressRepository.save(billingAddress);
		paymentRepository.save(payment);
		
		
		return order;
	}

	@Override
	public Order findOne(long id) {
		Optional<Order> orderResponse = orderRepository.findById(id);
		Order order = orderResponse.get();
		return order;
	}

}
