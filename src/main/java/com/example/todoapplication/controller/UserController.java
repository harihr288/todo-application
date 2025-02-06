package com.example.todoapplication.controller;

import com.example.todoapplication.dto.UserDto;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser=userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable Integer id){
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}