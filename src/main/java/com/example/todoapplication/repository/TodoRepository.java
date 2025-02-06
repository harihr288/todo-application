package com.example.todoapplication.repository;

import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Integer> {
    List<Todo> findByUser(User user);
}
