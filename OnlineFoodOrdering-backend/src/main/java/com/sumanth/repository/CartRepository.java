package com.sumanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long>{

	public Cart findByCustomerId(Long userId); 
}
