package com.cognixus.todo.repository;

import com.cognixus.todo.model.entity.TodoItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoItemRepository extends JpaRepository<TodoItemEntity,Long> {

    List<TodoItemEntity> findByUserIdAndIsDeletedFalse(String userId);
    Optional<TodoItemEntity> findByIdAndUserIdAndIsDeletedFalse(long id, String userId);
}
