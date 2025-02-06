package com.example.todoapplication.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String email;


    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDto(int i, String username, String email) {
    }

    public UserDto(String username, String password, @Email String email) {
    }
}
