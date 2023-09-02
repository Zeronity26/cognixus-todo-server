package com.cognixus.todo.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTodoItemRequest {
    @NotBlank(message="Todo item name must not be blank")
    private String name;

    @NotBlank(message="Todo item description must not be blank")
    @Size(max = 255, message = "Todo item description words cannot be larger than 255")
    private String description;

    private String userId;
}
