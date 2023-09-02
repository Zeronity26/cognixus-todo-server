package com.cognixus.todo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OauthTokenDto {
    private String accessToken;
    private Long expiresIn;
    private String tokenType;
    private String idToken;
}
