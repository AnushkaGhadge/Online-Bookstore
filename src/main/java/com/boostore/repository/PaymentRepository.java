package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
