package com.boostore.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boostore.domain.UserShipping;
import com.boostore.repository.UserShippingRepository;
import com.boostore.service.UserShippingService;

@Service
public class UserShippingServiceImpl implements UserShippingService {

	@Autowired
	private UserShippingRepository userShippingRepository;
	
	@Override
	public UserShipping findById(Long id) {
		Optional<UserShipping> userShippingResponse = userShippingRepository.findById(id);
		UserShipping userShipping = userShippingResponse.get();
		return userShipping;
	}

	@Override
	public void removeById(Long shippingAddressId) {
		userShippingRepository.deleteById(shippingAddressId);
	}

}
