package com.cognixus.todo.controller;

import com.cognixus.todo.exception.TodoItemNotFoundException;
import com.cognixus.todo.model.dto.TodoItemDto;
import com.cognixus.todo.model.request.AddTodoItemRequest;
import com.cognixus.todo.model.view.TodoItemView;
import com.cognixus.todo.service.TodoItemService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoItemService todoItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addTodoItem(@Valid @RequestBody AddTodoItemRequest request, Principal principal){
        request.setUserId(principal.getName());
        TodoItemDto todoItem = todoItemService.addTodoItem(request);

        return String.format("Successfully created todo item with id: %s", todoItem.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteTodoItemById(@PathVariable("id") long id, Principal principal){
        try{
            todoItemService.deleteTodoItemByIdAndUserId(id,principal.getName());
            return ResponseEntity.ok(String.format("Successfully deleted todo item with id: %s", id));
        }catch(TodoItemNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(TodoItemView.Public.class)
    public List<TodoItemDto> findAllTodoItem(Principal principal){
        return todoItemService.findActiveTodoItemByUserId(principal.getName());
    }

    @PatchMapping("/{id}/completed")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> patchTodoItemCompleted(@PathVariable("id") long id, Principal principal){
        try{
            todoItemService.markTodoItemCompletedByIdAndUserId(id,principal.getName());
            return ResponseEntity.ok(String.format("Successfully mark todo item as completed with id: %s", id));
        }catch(TodoItemNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
