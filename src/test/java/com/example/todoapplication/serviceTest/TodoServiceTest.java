package com.example.todoapplication.serviceTest;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.entity.User;
import com.example.todoapplication.repository.TodoRepository;
import com.example.todoapplication.repository.UserRepository;
import com.example.todoapplication.service.TodoService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TodoService todoService;

    private User testUser;
    private Todo testTodo;
    private TodoDto testTodoDto;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("hari123");

        testTodo = new Todo();
        testTodo.setId(1);
        testTodo.setTitle("Test Todo");
        testTodo.setDescription("Test Description");
        testTodo.setCompleted(false);
        testTodo.setUser(testUser);

        testTodoDto = new TodoDto();
        testTodoDto.setTitle("Updated Todo");
        testTodoDto.setDescription("Updated Description");
        testTodoDto.setCompleted(true);
    }

    @Test
     void createTodoTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

        TodoDto result = todoService.createTodo(1, testTodoDto);

        assertNotNull(result);
        assertEquals(testTodo.getTitle(), result.getTitle());
    }

    @Test
     void getTodoByUserIdTest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(todoRepository.findByUser(testUser)).thenReturn(Arrays.asList(testTodo));

        List<TodoDto> result = todoService.getTodoByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
     void updateTodoTest() {
        Todo existingTodo = testTodo;
        when(todoRepository.findById(1)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        Optional<TodoDto> result = todoService.updateTodo(1, testTodoDto);

        assertTrue(result.isPresent());
        assertEquals(testTodoDto.getTitle(), result.get().getTitle());
    }

    @Test
     void deleteTodoTest() {
        when(todoRepository.existsById(1)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(1);

        boolean result = todoService.deleteTodo(1);

        assertTrue(result);
        verify(todoRepository,times(1)).deleteById(1);
    }

}