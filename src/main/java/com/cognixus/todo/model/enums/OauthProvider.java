package com.cognixus.todo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OauthProvider {
    GOOGLE("google","https://accounts.google.com/o/oauth2/auth", "https://oauth2.googleapis.com/token");

    private final String code;
    private final String authUrl;
    private final String tokenUrl;

    public static OauthProvider findByCode(String code){
        return Stream.of(OauthProvider.values()).filter(provider -> provider.code.equals(code)).findFirst().orElse(null);
    }
}
