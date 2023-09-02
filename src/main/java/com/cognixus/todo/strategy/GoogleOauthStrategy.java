package com.cognixus.todo.strategy;

import com.cognixus.todo.model.dto.OauthTokenDto;
import com.cognixus.todo.model.enums.OauthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.cognixus.todo.model.enums.OauthProvider.GOOGLE;

@Component
public class GoogleOauthStrategy implements OauthStrategy {
    @Value("${application.base.url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OauthProvider getOauthProvider(){ return GOOGLE;}
    @Override
    public String generateAuthUrl(){
        return GOOGLE.getAuthUrl() +
                "?client_id=" + clientId +
                "&redirect_uri=" + baseUrl +
                "/api/oauth/google/callback&scope=openid profile email&response_type=code";
    }

    @Override
    public OauthTokenDto generateAccessTokenByCode(String code){
        String tokenUrl = GOOGLE.getTokenUrl() +
                "?code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + baseUrl +
                "/api/oauth/google/callback&scope=openid profile email&grant_type=authorization_code";
        return restTemplate.exchange(tokenUrl, HttpMethod.POST, null, OauthTokenDto.class).getBody();
    }
}
