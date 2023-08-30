package com.cognixus.todo.service;

import com.cognixus.todo.exception.TodoItemNotFoundException;
import com.cognixus.todo.model.dto.TodoItemDto;
import com.cognixus.todo.model.request.AddTodoItemRequest;

import java.util.List;

public interface TodoItemService {
    TodoItemDto addTodoItem(AddTodoItemRequest request);
    void deleteTodoItemByIdAndUserId(long id, String userId) throws TodoItemNotFoundException;
    List<TodoItemDto> findActiveTodoItemByUserId(String userId);
    void markTodoItemCompletedByIdAndUserId(long id, String userId) throws TodoItemNotFoundException;

}
