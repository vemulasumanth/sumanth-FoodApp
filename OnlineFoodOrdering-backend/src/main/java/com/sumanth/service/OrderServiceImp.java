package com.sumanth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumanth.model.Address;
import com.sumanth.model.Cart;
import com.sumanth.model.CartItem;
import com.sumanth.model.Order;
import com.sumanth.model.Orderitem;
import com.sumanth.model.Restaurant;
import com.sumanth.model.User;
import com.sumanth.repository.AddressRepository;
import com.sumanth.repository.OrderItemRepository;
import com.sumanth.repository.OrderRepository;
import com.sumanth.repository.RestaurantRepository;
import com.sumanth.repository.UserRepository;
import com.sumanth.request.OrderRequest;

@Service
public class OrderServiceImp implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception {
		// TODO Auto-generated method stub
		Address shipAddress=order.getDeliveryAddress();
		Address savedAddress=addressRepository.save(shipAddress);
		if(!user.getAddresses().contains(savedAddress))
		{
			user.getAddresses().add(savedAddress);
			userRepository.save(user);
		}
		Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
		Order createdOrder=new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		
		Cart cart=cartService.findCartByUserId(user.getId());
		List<Orderitem> orderItems=new ArrayList<>();
		for(CartItem cartItem:cart.getItem())
		{
			Orderitem orderitem=new Orderitem();
			orderitem.setFood(cartItem.getFood());
			orderitem.setIngredients(cartItem.getIngredients());
			orderitem.setQuantity(cartItem.getQuantity());
			orderitem.setTotalPrice(cartItem.getTotalPrice());
			
			Orderitem savedOrderitem=orderItemRepository.save(orderitem);
			orderItems.add(savedOrderitem);
		}
		long totalPrice=cartService.calculateCartTotals(cart);
		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrice(totalPrice);
		
		Order savedOrder=orderRepository.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		
		return createdOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		Order order=findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DELIVERY")|| orderStatus.equals("DELIVERED")|| orderStatus.equals("COMPLETED")
				|| orderStatus.equals("PENDING"))
		{
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("Please select a valid order status");
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		Order order=findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		List<Order> orders=orderRepository.findByRestaurantId(restaurantId);
		if(orderStatus!=null)
		{
			orders=orders.stream().filter(order->
			order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		Optional<Order> optionalOrder=orderRepository.findById(orderId);
		if(optionalOrder.isEmpty())
		{
			throw new Exception("order not found");
		}
		return optionalOrder.get();
	}

}
