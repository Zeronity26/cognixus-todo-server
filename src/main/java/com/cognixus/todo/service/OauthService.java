package com.cognixus.todo.service;

import com.cognixus.todo.model.dto.OauthTokenDto;

public interface OauthService {
    String findAuthUrlByProviderCode(String providerCode);
    OauthTokenDto findAccessTokenByProviderCode(String providerCode, String code);
}
