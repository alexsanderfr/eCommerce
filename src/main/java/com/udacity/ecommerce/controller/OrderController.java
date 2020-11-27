package com.udacity.ecommerce.controller;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);

	private final UserRepository userRepository;
	
	private final OrderRepository orderRepository;

	public OrderController(UserRepository userRepository, OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}


	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.info("ERROR: User not found");
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info("SUCCESS: Order was made");
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.info("ERROR: User not found");
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
