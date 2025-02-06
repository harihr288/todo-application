package com.example.todoapplication.service;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.TodoRepository;
import com.example.todoapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public Todo createTodo(Integer userId,Todo todo){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    public Page<Todo> getAllTodos(Pageable pageable){
        return todoRepository.findAll(pageable);
    }

    public List<Todo> getTodoByUserId(Integer userId){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not Found"));
        return todoRepository.findByUser(user);
    }

    public Optional<Todo> updateTodo(Integer todoId, TodoDto todoDto) {
        return todoRepository.findById(todoId).map(todo -> {
            todo.setTitle(todoDto.getTitle());
            todo.setDescription(todoDto.getDescription());
            todo.setCompleted(todoDto.isCompleted());
            return todoRepository.save(todo);
        });
    }

    public boolean deleteTodo(Integer todoId) {
        if(todoRepository.existsById(todoId)){
            todoRepository.deleteById(todoId);
            return true;
        }
        return false;
    }
}
