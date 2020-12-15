package com.boostore.service;

import com.boostore.domain.UserPayment;

public interface UserPaymentService {

	UserPayment findById(Long id);

	void removeById(Long creditCardId);
}
