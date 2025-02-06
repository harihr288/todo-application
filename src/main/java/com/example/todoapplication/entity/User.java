package com.example.todoapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    @Email
    private String email;

    @OneToMany(targetEntity = Todo.class,cascade = CascadeType.ALL)
    private List<Todo> todos=new ArrayList<>();

    public User(Integer userId, String username, String password, String email) {
    }

    public User(String username, String password, String email) {
    }
}
