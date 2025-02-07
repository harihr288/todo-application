package com.example.todoapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;


    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
