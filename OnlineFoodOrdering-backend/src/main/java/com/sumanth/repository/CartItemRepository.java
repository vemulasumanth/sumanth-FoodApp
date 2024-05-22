package com.sumanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
