package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.BillingAddress;

public interface BillingAddressRepository extends CrudRepository<BillingAddress, Long> {

}
