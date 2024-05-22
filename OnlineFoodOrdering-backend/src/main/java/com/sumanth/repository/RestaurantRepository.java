package com.sumanth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sumanth.model.Restaurant;
import com.sumanth.model.User;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>
{
	@Query("select r from Restaurant r where lower(r.name) like lower(concat('%',:query,'%')) "
			+ "or lower(r.cusineType) like lower(concat('%',:query,'%'))")
	List<Restaurant> findBySearchQuery(String query);
	
	Restaurant findByOwnerId(Long userId);
	
}