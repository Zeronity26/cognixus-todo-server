package com.cognixus.todo.controller;

import com.cognixus.todo.exception.TodoItemNotFoundException;
import com.cognixus.todo.model.dto.TodoItemDto;
import com.cognixus.todo.model.request.AddTodoItemRequest;
import com.cognixus.todo.service.TodoItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@Disabled
class TodoControllerTest {
    private static final String API_PATH_PREFIX = "/api/v1/todo";
    private static final String DELETE_API_PATH = API_PATH_PREFIX+"/1";
    private static final String PATCH_API_PATH = API_PATH_PREFIX+"/1/completed";
    @Value("${bearer.token}")
    private String bearerToken;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TodoItemService todoItemService;


    @Test
    void testAddTodoItemShouldReturn201ResponseStatus() throws Exception {
        AddTodoItemRequest request = AddTodoItemRequest.builder().name("12345").description("test desc 123").build();
        String requestBody = objectMapper.writeValueAsString(request);

        TodoItemDto todoItemDto = TodoItemDto.builder().id(1).userId("123456").name("12345").description("test desc 123").build();
        when(todoItemService.addTodoItem(any())).thenReturn(todoItemDto);
        mockMvc.perform(post(API_PATH_PREFIX).header("Authorization", "Bearer " + bearerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("Successfully created todo item with id: 1"))
                .andDo(print());
    }


    @Test
    void testAddTodoItemShouldReturn400ResponseStatus() throws Exception {
        AddTodoItemRequest request = AddTodoItemRequest.builder().name("").description("test desc 123").build();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(API_PATH_PREFIX).header("Authorization", "Bearer " + bearerToken)
                                .contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Todo item name must not be blank"))
                .andDo(print());
    }

    @Test
    void testDeleteTodoItemByIdShouldReturn200ResponseStatus() throws Exception {
        doNothing().when(todoItemService).deleteTodoItemByIdAndUserId(anyLong(),anyString());
        mockMvc.perform(delete(DELETE_API_PATH).header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully deleted todo item with id: 1"))
                .andDo(print());
    }

    @Test
    void testDeleteTodoItemByIdShouldReturn404ResponseStatus() throws Exception {
        doThrow(new TodoItemNotFoundException()).when(todoItemService).deleteTodoItemByIdAndUserId(anyLong(),anyString());
        mockMvc.perform(delete(DELETE_API_PATH).header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Todo Item not found"))
                .andDo(print());
    }

    @Test
    void testFindAllTodoItemShouldReturn200ResponseStatus() throws Exception {
        List<TodoItemDto> todoItems = List.of(
                TodoItemDto.builder().id(1).userId("123456").name("12345").description("test desc 123").build(),
                TodoItemDto.builder().id(2).userId("123456").name("34567").description("test desc 345").build());
        when(todoItemService.findActiveTodoItemByUserId(anyString())).thenReturn(todoItems);
        mockMvc.perform(get(API_PATH_PREFIX).header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andDo(print());
    }

    @Test
    void testPatchTodoItemCompletedShouldReturn200ResponseStatus() throws Exception {
        doNothing().when(todoItemService).markTodoItemCompletedByIdAndUserId(anyLong(),anyString());
        mockMvc.perform(patch(PATCH_API_PATH).header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully mark todo item as completed with id: 1"))
                .andDo(print());
    }

    @Test
    void testPatchTodoItemCompletedShouldReturn404ResponseStatus() throws Exception {
        doThrow(new TodoItemNotFoundException()).when(todoItemService).markTodoItemCompletedByIdAndUserId(anyLong(),anyString());
        mockMvc.perform(patch(PATCH_API_PATH).header("Authorization", "Bearer " + bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Todo Item not found"))
                .andDo(print());
    }
}
