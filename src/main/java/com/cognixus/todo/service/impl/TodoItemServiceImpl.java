package com.cognixus.todo.service.impl;

import com.cognixus.todo.exception.TodoItemNotFoundException;
import com.cognixus.todo.model.dto.TodoItemDto;
import com.cognixus.todo.model.entity.TodoItemEntity;
import com.cognixus.todo.model.enums.TodoItemStatus;
import com.cognixus.todo.model.request.AddTodoItemRequest;
import com.cognixus.todo.repository.TodoItemRepository;
import com.cognixus.todo.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {
    private final TodoItemRepository todoItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public TodoItemDto addTodoItem(AddTodoItemRequest request){
        TodoItemEntity todoItem = todoItemRepository.save(TodoItemEntity.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .description(request.getDescription())
                .status(TodoItemStatus.PENDING)
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false).build());
        return modelMapper.map(todoItem, TodoItemDto.class);
    }

    @Override
    public void deleteTodoItemByIdAndUserId(long id, String userId) throws TodoItemNotFoundException {
        todoItemRepository.findByIdAndUserIdAndIsDeletedFalse(id,userId).map(todoItem -> {
            todoItem.setDeleted(true);
            todoItem.setUpdateTime(new Date());
            return todoItemRepository.save(todoItem);
        }).orElseThrow(TodoItemNotFoundException::new);
    }
    @Override
    public List<TodoItemDto> findActiveTodoItemByUserId(String userId){
        return todoItemRepository.findByUserIdAndIsDeletedFalse(userId).stream()
                .map(item -> modelMapper.map(item, TodoItemDto.class)).toList();
    }

    @Override
    public void markTodoItemCompletedByIdAndUserId(long id, String userId) throws TodoItemNotFoundException {
        todoItemRepository.findByIdAndUserIdAndIsDeletedFalse(id,userId).map(todoItem -> {
            todoItem.setStatus(TodoItemStatus.COMPLETED);
            todoItem.setUpdateTime(new Date());
            return todoItemRepository.save(todoItem);
        }).orElseThrow(TodoItemNotFoundException::new);
    }
}
