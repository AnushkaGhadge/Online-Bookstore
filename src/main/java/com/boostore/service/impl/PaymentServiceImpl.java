package com.boostore.service.impl;

import org.springframework.stereotype.Service;

import com.boostore.domain.Payment;
import com.boostore.domain.UserPayment;
import com.boostore.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Override
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvv(userPayment.getCvv());
		
		return payment;
	}

}
