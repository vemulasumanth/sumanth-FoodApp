package com.sumanth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sumanth.model.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

	List<Food> findByRestaurantId(Long restaurantId);
	
	@Query("select f from Food f where f.name like %:keyword% or f.foodCategory.name like %:keyword%")
	List<Food> searchFood(@Param("keyword") String keyword);
}
