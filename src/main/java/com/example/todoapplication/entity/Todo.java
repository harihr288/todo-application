package com.example.todoapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @Size(max = 100, message = "Description maximum size should be 100")
    private String description;
    private boolean completed;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
