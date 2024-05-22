package com.sumanth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumanth.model.IngredientCategory;
import com.sumanth.model.IngredientsItem;
import com.sumanth.model.Restaurant;
import com.sumanth.repository.IngredientCategoryRepository;
import com.sumanth.repository.IngredientItemRepository;

@Service
public class IngredientServiceImp implements IngredientService{

	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category=new IngredientCategory();
		category.setRestaurant(restaurant);
		category.setName(name);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientCategory> opt=ingredientCategoryRepository.findById(id);
		if(opt.isEmpty())
		{
			throw new Exception("ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		// TODO Auto-generated method stub
		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientsItem item=new IngredientsItem();
		item.setRestaurant(restaurant);
		IngredientCategory category=findIngredientCategoryById(categoryId);
		item.setCategory(category);
		item.setName(ingredientName);
		IngredientsItem ingredient=ingredientItemRepository.save(item);
		category.getIngredients().add(ingredient);
		return ingredient;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
		// TODO Auto-generated method stub
		
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientsItem> opt=ingredientItemRepository.findById(id);
		if(opt.isEmpty())
		{
			throw new Exception("ingredient not found");
		}
		IngredientsItem ingredientsItem=opt.get();
		ingredientsItem.setInStock(!ingredientsItem.isInStock());
		return ingredientItemRepository.save(ingredientsItem);
	}
}
