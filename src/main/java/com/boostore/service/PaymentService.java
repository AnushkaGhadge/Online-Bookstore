package com.boostore.service;

import com.boostore.domain.Payment;
import com.boostore.domain.UserPayment;

public interface PaymentService {

	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
