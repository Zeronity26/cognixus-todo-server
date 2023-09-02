package com.cognixus.todo.strategy;

import com.cognixus.todo.model.enums.OauthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OauthStrategyFactory {
    private final Map<OauthProvider, OauthStrategy> oauthStrategyMap;

    @Autowired
    public OauthStrategyFactory(Set<OauthStrategy> oauthStrategySet){
       oauthStrategyMap = oauthStrategySet.stream().collect(Collectors.toMap(OauthStrategy::getOauthProvider, Function.identity()));
    }

    public OauthStrategy getOauthStrategy(OauthProvider oauthProvider){
        return Optional.ofNullable(oauthStrategyMap.get(oauthProvider))
                .orElseThrow(()-> new IllegalStateException("No strategy found for oauth provider " + oauthProvider));
    }
}
