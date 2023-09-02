package com.cognixus.todo.strategy;

import com.cognixus.todo.model.dto.OauthTokenDto;
import com.cognixus.todo.model.enums.OauthProvider;

public interface OauthStrategy {
    OauthProvider getOauthProvider();
    String generateAuthUrl();
    OauthTokenDto generateAccessTokenByCode(String code);
}
