package com.udacity.ecommerce.model.persistence.repositories;

import java.util.List;

import com.udacity.ecommerce.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.ecommerce.model.persistence.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
