package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.Order;


public interface OrderRepository extends CrudRepository<Order, Long> {

}
