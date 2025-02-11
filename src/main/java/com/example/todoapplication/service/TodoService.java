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

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Autowired
    TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public TodoDto createTodo(Integer userId, TodoDto todoDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Todo todo = new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);
        return new TodoDto(savedTodo.getTitle(), savedTodo.getDescription(), savedTodo.isCompleted());
    }

    public Page<TodoDto> getAllTodos(Pageable pageable) {
        Page<Todo> todos = todoRepository.findAll(pageable);

        return todos.map(todo -> new TodoDto(todo.getTitle(), todo.getDescription(), todo.isCompleted()));
    }

    public List<TodoDto> getTodoByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<Todo> todos = todoRepository.findByUser(user);

        return todos.stream()
                .map(todo -> new TodoDto(todo.getTitle(), todo.getDescription(), todo.isCompleted())).toList();
    }

    public Optional<TodoDto> updateTodo(Integer todoId, TodoDto todoDto) {
        return todoRepository.findById(todoId).map(todo -> {
            todo.setTitle(todoDto.getTitle());
            todo.setDescription(todoDto.getDescription());
            todo.setCompleted(todoDto.isCompleted());

            Todo updatedTodo = todoRepository.save(todo);
            return new TodoDto(updatedTodo.getTitle(), updatedTodo.getDescription(), updatedTodo.isCompleted());
        });
    }

    public boolean deleteTodo(Integer todoId) {
        if (todoRepository.existsById(todoId)) {
            todoRepository.deleteById(todoId);
            return true;
        }
        return false;
    }
}
