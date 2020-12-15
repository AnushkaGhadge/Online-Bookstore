package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.ShippingAddress;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {

}
