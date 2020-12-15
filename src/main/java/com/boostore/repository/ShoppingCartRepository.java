package com.boostore.repository;

import org.springframework.data.repository.CrudRepository;

import com.boostore.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
