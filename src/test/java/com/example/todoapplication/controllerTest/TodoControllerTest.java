package com.example.todoapplication.controllerTest;

import com.example.todoapplication.dto.TodoDto;
import com.example.todoapplication.entity.Todo;
import com.example.todoapplication.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createTodoTest() throws Exception {
        Integer userId = 1;
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setCompleted(false);

        Todo savedTodo = new Todo();
        savedTodo.setId(1);
        savedTodo.setTitle("Test Todo");
        savedTodo.setDescription("Test Description");
        savedTodo.setCompleted(false);

        when(todoService.createTodo(eq(userId), any(Todo.class))).thenReturn(savedTodo);

        mockMvc.perform(post("/todo/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    public void getAllTodosTest() throws Exception {
        List<Todo> todos = Arrays.asList(
                new Todo(1, "Todo 1", "Desc 1", false),
                new Todo(2, "Todo 2", "Desc 2", true)
        );
        Page<Todo> todoPage = new PageImpl<>(todos, PageRequest.of(0, 2), todos.size());

        when(todoService.getAllTodos(any(PageRequest.class))).thenReturn(todoPage);

        mockMvc.perform(get("/todos")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    public void getTodoByUserIdTest() throws Exception {
        Integer userId = 1;
        List<Todo> todos = Arrays.asList(
                new Todo(1, "Todo 1", "Desc 1", false),
                new Todo(2, "Todo 2", "Desc 2", true)
        );

        when(todoService.getTodoByUserId(userId)).thenReturn(todos);

        mockMvc.perform(get("/todo/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void updateTodoTest() throws Exception {
        Integer todoId = 1;
        TodoDto todoDto = new TodoDto("Updated Todo", "Updated Description", true);

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setCompleted(true);

        when(todoService.updateTodo(eq(todoId), any(TodoDto.class)))
                .thenReturn(Optional.of(updatedTodo));

        mockMvc.perform(put("/todo/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        Integer todoId = 1;
        when(todoService.deleteTodo(todoId)).thenReturn(true);

        mockMvc.perform(delete("/todo/" + todoId))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo Deleted Successfully"));
    }
}