package com.boostore.service;

import com.boostore.domain.BillingAddress;
import com.boostore.domain.UserBilling;

public interface BillingAddressService {

	
	BillingAddress setByUserBilling(BillingAddress billingAddress, UserBilling userBilling);
}
