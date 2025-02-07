package com.example.todoapplication.controllerTest;

import com.example.todoapplication.dto.TodoDto;
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
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void createTodoTest() throws Exception {
        Integer userId = 1;

        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("Test Todo");
        todoDto.setDescription("Test Description");
        todoDto.setCompleted(false);

        TodoDto savedTodoDto = new TodoDto(1, "Test Todo", "Test Description", false);

        when(todoService.createTodo(eq(userId), any(TodoDto.class))).thenReturn(savedTodoDto);

        mockMvc.perform(post("/todo/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTodosTest() throws Exception {
        List<TodoDto> todoDtos = Arrays.asList(
                new TodoDto(1, "Todo 1", "Desc 1", false),
                new TodoDto(2, "Todo 2", "Desc 2", true)
        );
        Page<TodoDto> todoDtoPage = new PageImpl<>(todoDtos, PageRequest.of(0, 2), todoDtos.size());

        when(todoService.getAllTodos(any(PageRequest.class))).thenReturn(todoDtoPage);

        mockMvc.perform(get("/todos")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void getTodoByUserIdTest() throws Exception {
        Integer userId = 1;
        List<TodoDto> todoDtos = Arrays.asList(
                new TodoDto(1, "Todo 1", "Desc 1", false),
                new TodoDto(2, "Todo 2", "Desc 2", true)
        );

        when(todoService.getTodoByUserId(userId)).thenReturn(todoDtos);

        mockMvc.perform(get("/todo/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void updateTodoTest() throws Exception {
        Integer todoId = 1;
        TodoDto todoDto = new TodoDto("Updated Todo", "Updated Description", true);

        TodoDto updatedTodoDto = new TodoDto(todoId, "Updated Todo", "Updated Description", true);

        when(todoService.updateTodo(eq(todoId), any(TodoDto.class)))
                .thenReturn(Optional.of(updatedTodoDto));

        mockMvc.perform(put("/todo/" + todoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTodoTest() throws Exception {
        Integer todoId = 1;
        when(todoService.deleteTodo(todoId)).thenReturn(true);

        mockMvc.perform(delete("/todo/" + todoId))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo Deleted Successfully"));
    }
}
