package com.udacity.ecommerce.controller;

import com.udacity.ecommerce.model.persistence.User;
import com.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.ecommerce.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserControllerTests {
    private UserController userController;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController(userRepository, cartRepository, bCryptPasswordEncoder);
    }

    @Test
    public void createUserTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("John");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");
        Mockito.when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("hashedPassword");

        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User user = responseEntity.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals(createUserRequest.getUsername(), user.getUsername());
        assertEquals("hashedPassword", user.getPassword());
    }

    @Test
    public void createUserWrongConfirmPasswordTest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("John");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("wrongPassword");
        Mockito.when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("hashedPassword");

        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void findByIdTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("testPassword");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ResponseEntity<User> findUserResponseEntity = userController.findById(user.getId());
        assertNotNull(findUserResponseEntity);
        assertEquals(HttpStatus.OK, findUserResponseEntity.getStatusCode());
        assertEquals(user, findUserResponseEntity.getBody());
    }

    @Test
    public void findByUsernameTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("John");
        user.setPassword("testPassword");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        ResponseEntity<User> findUserResponseEntity = userController.findByUserName(user.getUsername());
        assertNotNull(findUserResponseEntity);
        assertEquals(HttpStatus.OK, findUserResponseEntity.getStatusCode());
        assertEquals(user, findUserResponseEntity.getBody());
    }
}
