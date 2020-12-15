package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.UserPayment;

public interface UserPaymentRepository extends CrudRepository<UserPayment, Long>{

}
