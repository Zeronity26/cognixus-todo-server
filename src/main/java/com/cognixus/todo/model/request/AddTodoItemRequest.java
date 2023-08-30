package com.cognixus.todo.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddTodoItemRequest {
    @NotBlank(message="Todo item name must not be blank")
    private String name;

    @NotBlank(message="Todo item description must not be blank")
    @Size(max = 255, message = "Todo item description words cannot be larger than 255")
    private String description;

    private String userId;
}
