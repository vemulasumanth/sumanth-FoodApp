package com.sumanth.service;

import java.util.List;

import com.sumanth.model.Category;
import com.sumanth.model.Food;
import com.sumanth.model.Restaurant;
import com.sumanth.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant);
	
	public void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantsFood(Long restaurantId,
			boolean isvegitarian,
			boolean isNonveg,
			boolean isSeasonal,
			String foodCategory);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
