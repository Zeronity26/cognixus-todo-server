package com.cognixus.todo.model.request;

import lombok.Data;

@Data
public class OauthCallbackRequest {
    private String code;
}
