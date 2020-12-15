package com.boostore.service;

import java.util.Set;

import com.boostore.domain.User;
import com.boostore.domain.UserBilling;
import com.boostore.domain.UserPayment;
import com.boostore.domain.UserShipping;
import com.boostore.domain.security.PasswordResetToken;
import com.boostore.domain.security.UserRole;

public interface UserService {
	
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail(String email);

	User createUser(User user, Set<UserRole> userRoles) throws Exception;

	User save(User user);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void setUserDefaultPayment(Long defaultPaymentId, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultShipping(Long defaultShippingAddressId, User user);

	User findById(Long id);
}
