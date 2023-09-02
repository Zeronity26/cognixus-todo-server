package com.cognixus.todo.service.impl;

import com.cognixus.todo.exception.TodoItemNotFoundException;
import com.cognixus.todo.model.dto.TodoItemDto;
import com.cognixus.todo.model.entity.TodoItemEntity;
import com.cognixus.todo.model.enums.TodoItemStatus;
import com.cognixus.todo.model.request.AddTodoItemRequest;
import com.cognixus.todo.repository.TodoItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoItemServiceImplTest {
    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TodoItemRepository todoItemRepository;

    private static List<TodoItemEntity> todoItemEntities;
    private static List<TodoItemDto> todoItemDtos;

    @BeforeAll
    static void init() {
        todoItemEntities = List.of(
                TodoItemEntity.builder()
                .id(1).userId("11111").name("test todo name")
                .description("test todo description")
                .status(TodoItemStatus.PENDING)
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build());

        todoItemDtos = List.of(
                TodoItemDto.builder()
                .id(1).userId("11111").name("test todo name")
                .description("test todo description")
                .status(TodoItemStatus.PENDING)
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build());
    }

    @Test
    void testAddTodoItemShouldReturnCorrectResult(){
        AddTodoItemRequest request = AddTodoItemRequest.builder()
                .userId("11111").name("test todo name").description("test todo description").build();
        when(todoItemRepository.save(any(TodoItemEntity.class))).thenReturn(todoItemEntities.get(0));
        when(modelMapper.map(any(TodoItemEntity.class),any())).thenReturn(todoItemDtos.get(0));
        TodoItemDto todoItemDto = todoItemService.addTodoItem(request);
        assertNotNull(todoItemDto);
        assertEquals("11111",todoItemDto.getUserId());
    }

    @Test
    void testFindActiveTodoItemByUserIdShouldReturnCorrectResults(){
        when(todoItemRepository.findByUserIdAndIsDeletedFalse(anyString())).thenReturn(todoItemEntities);
        when(modelMapper.map(any(),any())).thenReturn(todoItemDtos.get(0));
        List<TodoItemDto> todoItems = todoItemService.findActiveTodoItemByUserId("11111");
        assertFalse(todoItems.isEmpty());
        assertEquals(1,todoItems.size());
    }

    @Test
    void testMarkTodoItemCompletedByIdAndUserIdShouldThrowException(){
        when(todoItemRepository.findByIdAndUserIdAndIsDeletedFalse(anyLong(),anyString())).thenReturn(Optional.empty());
        assertThrows(TodoItemNotFoundException.class, ()-> todoItemService.markTodoItemCompletedByIdAndUserId(2,"12345"));
    }
}
