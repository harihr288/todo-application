package com.example.todoapplication.controller;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/todo/{userId}")
    public ResponseEntity<TodoDto> createTodo(@PathVariable Integer userId, @RequestBody TodoDto todoDto) {
        TodoDto savedTodo = todoService.createTodo(userId, todoDto);
        return ResponseEntity.ok(new TodoDto(savedTodo.getTitle(), savedTodo.getDescription(), savedTodo.isCompleted()));
    }

    @GetMapping("/todos")
    public Page<TodoDto> getAllTodos(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TodoDto> todoPage = todoService.getAllTodos(pageable);
        Page<TodoDto> todoDtoPage = todoPage.map(todo -> new TodoDto(todo.getTitle(), todo.getDescription(), todo.isCompleted()));

        return todoDtoPage;
    }

    @GetMapping("/todo/{userId}")
    public ResponseEntity<List<TodoDto>> getTodoByUserId(@PathVariable Integer userId) {
        List<TodoDto> todos = todoService.getTodoByUserId(userId).stream()
                .map(todo -> new TodoDto(todo.getTitle(), todo.getDescription(), todo.isCompleted()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(todos);
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable Integer todoId, @RequestBody TodoDto todoDto) {
        Optional<TodoDto> updatedTodo = todoService.updateTodo(todoId, todoDto);

        return updatedTodo.map(todo -> ResponseEntity.ok(
                        new TodoDto(todo.getTitle(), todo.getDescription(), todo.isCompleted())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable Integer todoId) {
        boolean deleted = todoService.deleteTodo(todoId);

        if (deleted)
            return ResponseEntity.ok("Todo Deleted Successfully");
        else
            return ResponseEntity.notFound().build();
    }
}

