package com.cognixus.todo.exception;

public class TodoItemNotFoundException extends Exception{

    public TodoItemNotFoundException(){
        super("Todo Item not found");
    }

    public TodoItemNotFoundException(String message){
        super(message);
    }
}
