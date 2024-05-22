package com.sumanth.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumanth.model.Category;
import com.sumanth.model.Food;
import com.sumanth.model.Restaurant;
import com.sumanth.repository.FoodRepository;
import com.sumanth.request.CreateFoodRequest;

@Service
public class FoodServiceImpl implements FoodService{

	@Autowired
	private FoodRepository foodRepository;
	
	
	
	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		// TODO Auto-generated method stub
		Food food =new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegetarian(req.isVegetarian());
		food.setCreationDate(new Date());
		Food savedFood=foodRepository.save(food);
		restaurant.getFoods().add(savedFood);
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Food food=findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.save(food);
	}

	@Override
	public List<Food> getRestaurantsFood(Long restaurantId, boolean isvegitarian, boolean isNonveg, boolean isSeasonal,
			String foodCategory) {
		// TODO Auto-generated method stub
		List<Food> foods=foodRepository.findByRestaurantId(restaurantId);
		
		if(isvegitarian)
		{
			foods=filterByVegetarian(foods,isvegitarian);
		}
		if(isNonveg)
		{
			foods=filterByNonVeg(foods,isNonveg);
		}
		if(isSeasonal)
		{
			foods=filterBySeasonal(foods,isSeasonal);
		}
		if(foodCategory!=null && !foodCategory.equals(""))
		{
			foods=filterByCategory(foods,foodCategory);
		}
		return foods;
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonveg) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->food.isVegetarian()==!(isNonveg)).collect(Collectors.toList());
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		// TODO Auto-generated method stub
		
		return foods.stream().filter(food->{
			if(food.getFoodCategory()!=null)
			{
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isvegitarian) {
		// TODO Auto-generated method stub
		
		return foods.stream().filter(food->food.isVegetarian()==isvegitarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		// TODO Auto-generated method stub
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Optional<Food> optionalFood=foodRepository.findById(foodId);
		if(optionalFood.isEmpty())
		{
			throw new Exception("food not exist");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailabilityStatus(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Food food=findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		
		return foodRepository.save(food);
	}

}
