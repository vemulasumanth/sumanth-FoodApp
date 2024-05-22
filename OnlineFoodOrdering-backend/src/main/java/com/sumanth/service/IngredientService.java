package com.sumanth.service;

import java.util.List;

import com.sumanth.model.IngredientCategory;
import com.sumanth.model.IngredientsItem;

//for both ingredientcategory and ingredientitem 

public interface IngredientService {

	public IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;
	
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception;
	
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;
	
	public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;
	
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);
	
	public IngredientsItem updateStock(Long id) throws Exception;
}
