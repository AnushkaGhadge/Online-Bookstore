package com.boostore.service;

import com.boostore.domain.ShippingAddress;
import com.boostore.domain.UserShipping;

public interface ShippingAddressService {

	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
