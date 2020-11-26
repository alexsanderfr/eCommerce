package com.udacity.ecommerce.controllers;

import com.udacity.ecommerce.model.persistence.Cart;
import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.UserOrder;
import com.udacity.ecommerce.model.persistence.repositories.OrderRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderControllerTest {
    private OrderController orderController;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController(userRepository, orderRepository);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setTotal(BigDecimal.ZERO);

        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("testPassword");
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
    }

    @Test
    public void submitTest() {
        ResponseEntity<UserOrder> responseEntity = orderController.submit("John");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getOrdersFromUserTest() {
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("John");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
