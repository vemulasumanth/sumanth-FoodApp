package com.sumanth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sumanth.model.Orderitem;

public interface OrderItemRepository extends JpaRepository<Orderitem, Long>{

}
