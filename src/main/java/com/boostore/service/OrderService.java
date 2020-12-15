package com.boostore.service;

import com.boostore.domain.BillingAddress;
import com.boostore.domain.Order;
import com.boostore.domain.Payment;
import com.boostore.domain.ShippingAddress;
import com.boostore.domain.ShoppingCart;
import com.boostore.domain.User;

public interface OrderService {

//	Order createOrder(ShoppingCart shoppingCart, BillingAddress billingAddress, ShippingAddress shippingAddress,
//			Payment payment, String shippingMethod, User user);

	Order findOne(long id);

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);
}
