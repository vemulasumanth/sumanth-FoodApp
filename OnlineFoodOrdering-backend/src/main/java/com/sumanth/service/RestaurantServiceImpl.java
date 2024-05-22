package com.sumanth.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumanth.model.Address;
import com.sumanth.model.Restaurant;
import com.sumanth.model.RestaurantDto;
import com.sumanth.model.User;
import com.sumanth.repository.AddressRepository;
import com.sumanth.repository.RestaurantRepository;
import com.sumanth.repository.UserRepository;
import com.sumanth.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements RestaurantService{

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		// TODO Auto-generated method stub
		Address address=addressRepository.save(req.getAddress());
		Restaurant restaurant=new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCusineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantRepository.findByOwnerId(restaurantId); 
		if(restaurant.getCusineType()!=null)
		{
			restaurant.setCusineType(updateRestaurant.getCuisineType());
		}
		if(restaurant.getDescription()!=null)
		{
			restaurant.setDescription(updateRestaurant.getDescription());
		}
		if(restaurant.getName()!=null)
		{
			restaurant.setName(updateRestaurant.getName());
		}
		
			restaurant.setContactInformation(updateRestaurant.getContactInformation());
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(restaurantId);
		restaurantRepository.delete(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		// TODO Auto-generated method stub
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Restaurant> opt=restaurantRepository.findById(id);
		if(opt.isEmpty())
		{
			throw new Exception("restaurant not found with id"+id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
		if(restaurant==null)
		{
			throw new Exception("restaurant not found with ownerid"+userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(restaurantId);
		RestaurantDto dto=new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
		boolean isFavorited=false;
		List<RestaurantDto> favorites=user.getFavourites();
		for(RestaurantDto favorite:favorites)
		{
			if(favorite.getId().equals(restaurantId))
			{
				isFavorited=true;
				break;
			}
		}
		if(isFavorited)
		{
			favorites.removeIf(favorite->favorite.getId().equals(restaurantId));
		}
		else
		{
			favorites.add(dto);
		}
		userRepository.save(user);
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen());
		return restaurantRepository.save(restaurant);
	}

}
