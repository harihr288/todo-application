package com.example.todoapplication.serviceTest;

import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.UserRepository;
import com.example.todoapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;


    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(testUser.getId(), result.get().getId());
    }

    @Test
    void getAllUsersTest() {
        List<User> userList = Arrays.asList(testUser);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void createUserTest() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result=userService.createUser(testUser);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());

    }
}


