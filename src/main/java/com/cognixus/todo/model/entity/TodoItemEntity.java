package com.cognixus.todo.model.entity;


import com.cognixus.todo.model.enums.TodoItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_todo_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TodoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TodoItemStatus status;

    @Column(name="create_time", nullable = false, updatable = false)
    private Date createTime;

    @Column(name="update_time", nullable = false)
    private Date updateTime;

    @Column(name="is_deleted", nullable = false)
    private boolean isDeleted;
}
