package com.cognixus.todo.model.dto;


import com.cognixus.todo.model.enums.TodoItemStatus;
import com.cognixus.todo.model.view.TodoItemView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoItemDto implements Serializable {
    @JsonView(TodoItemView.Public.class)
    private long id;
    private String userId;
    @JsonView(TodoItemView.Public.class)
    private String name;
    @JsonView(TodoItemView.Public.class)
    private String description;
    @JsonView(TodoItemView.Public.class)
    private TodoItemStatus status;
    @JsonView(TodoItemView.Public.class)
    private Date createTime;
    @JsonView(TodoItemView.Public.class)
    private Date updateTime;
    private boolean isDeleted;
}
