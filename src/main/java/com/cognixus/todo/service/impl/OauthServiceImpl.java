package com.cognixus.todo.service.impl;

import com.cognixus.todo.model.dto.OauthTokenDto;
import com.cognixus.todo.model.enums.OauthProvider;
import com.cognixus.todo.service.OauthService;
import com.cognixus.todo.strategy.OauthStrategy;
import com.cognixus.todo.strategy.OauthStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {
    private final OauthStrategyFactory oauthStrategyFactory;

    public String findAuthUrlByProviderCode(String providerCode){
        OauthStrategy oauthStrategy = oauthStrategyFactory.getOauthStrategy(OauthProvider.findByCode(providerCode));
        return oauthStrategy.generateAuthUrl();
    }

    public OauthTokenDto findAccessTokenByProviderCode(String providerCode, String code){
        OauthStrategy oauthStrategy = oauthStrategyFactory.getOauthStrategy(OauthProvider.findByCode(providerCode));
        return oauthStrategy.generateAccessTokenByCode(code);
    }
}
