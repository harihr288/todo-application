package com.example.todoapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private String title;
    private String description;
    private boolean completed;

    public TodoDto(int i, String title, String description, boolean completed) {
    }

}
