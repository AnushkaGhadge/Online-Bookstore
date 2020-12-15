package com.boostore.service.impl;

import org.springframework.stereotype.Service;

import com.boostore.domain.BillingAddress;
import com.boostore.domain.UserBilling;
import com.boostore.service.BillingAddressService;

@Service
public class BillingAddressImpl implements BillingAddressService {

	@Override
	public BillingAddress setByUserBilling(BillingAddress billingAddress, UserBilling userBilling) {
		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressState(userBilling.getUserBillingState());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		billingAddress.setBillingAddressZipCode(userBilling.getUserBillingZipCode());
		return billingAddress;
		
	}

}
