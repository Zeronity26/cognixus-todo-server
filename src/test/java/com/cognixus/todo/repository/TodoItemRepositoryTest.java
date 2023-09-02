package com.cognixus.todo.repository;

import com.cognixus.todo.model.entity.TodoItemEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql("classpath:todoItemDataset.sql")
class TodoItemRepositoryTest {
    @Autowired
    private TodoItemRepository todoItemRepository;

    @Test
    void testFindByUserIdAndIsDeletedFalseShouldReturnCorrectResults(){
        List<TodoItemEntity> todoItems =  todoItemRepository.findByUserIdAndIsDeletedFalse("11111");
        assertFalse(todoItems.isEmpty());
        assertEquals(2,todoItems.size());
        assertFalse(todoItems.stream().anyMatch(todoItem -> todoItem.getId() == 2));
    }

    @Test
    void testFindByUserIdAndIsDeletedFalseShouldReturnEmpty(){
        List<TodoItemEntity> todoItems =  todoItemRepository.findByUserIdAndIsDeletedFalse("123456");
        assertTrue(todoItems.isEmpty());;
    }

    @Test
    void testFindByIdAndUserIdAndIsDeletedFalseShouldReturnCorrectResults(){
        Optional<TodoItemEntity> todoItems =  todoItemRepository.findByIdAndUserIdAndIsDeletedFalse(1,"11111");
        assertTrue(todoItems.isPresent());
        assertEquals("todo description 123",todoItems.get().getDescription());
    }

    @Test
    void testFindByIdAndUserIdAndIsDeletedFalseShouldReturnEmpty(){
        Optional<TodoItemEntity> todoItems =  todoItemRepository.findByIdAndUserIdAndIsDeletedFalse(2,"11111");
        assertTrue(todoItems.isEmpty());
    }
}
