package com.boostore.service;

import com.boostore.domain.UserShipping;

public interface UserShippingService {

	UserShipping findById(Long id);

	void removeById(Long shippingAddressId);
}
