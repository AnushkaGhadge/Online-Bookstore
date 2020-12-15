package com.boostore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boostore.domain.UserPayment;
import com.boostore.repository.UserPaymentRepository;
import com.boostore.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	@Override
	public UserPayment findById(Long id) {
		Optional<UserPayment> userPaymentResponse = userPaymentRepository.findById(id);
		UserPayment userPayment = userPaymentResponse.get();
		return userPayment;
	}

	@Override
	public void removeById(Long creditCardId) {
		userPaymentRepository.deleteById(creditCardId);
		
	}

}
