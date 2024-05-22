package com.sumanth.service;

import java.util.List;

import com.sumanth.model.Restaurant;
import com.sumanth.model.RestaurantDto;
import com.sumanth.model.User;
import com.sumanth.request.CreateRestaurantRequest;

public interface RestaurantService {

	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updateRestaurant) throws Exception;
	
	public void deleteRestaurant(Long restaurantId) throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long id) throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	public RestaurantDto addToFavourites(Long restaurantId,User user) throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
