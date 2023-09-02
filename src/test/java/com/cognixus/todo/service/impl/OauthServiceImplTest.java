package com.cognixus.todo.service.impl;

import com.cognixus.todo.model.enums.OauthProvider;
import com.cognixus.todo.strategy.GoogleOauthStrategy;
import com.cognixus.todo.strategy.OauthStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"application.base.url=http://localhost:8080"})
class OauthServiceImplTest {

    @InjectMocks
    private OauthServiceImpl oauthService;
    @Mock
    private OauthStrategyFactory oauthStrategyFactory;
    @InjectMocks
    private GoogleOauthStrategy googleOauthStrategy;

    @Test
    void testFindAuthUrlByProviderCodeShouldReturnCorrectResult(){
        when(oauthStrategyFactory.getOauthStrategy(any(OauthProvider.class))).thenReturn(googleOauthStrategy);
        ReflectionTestUtils.setField(googleOauthStrategy, "clientId", "google-client-id");
        ReflectionTestUtils.setField(googleOauthStrategy, "baseUrl", "http://localhost:8080");
        assertEquals("https://accounts.google.com/o/oauth2/auth?client_id=google-client-id&redirect_uri=http://localhost:8080/api/oauth/google/callback&scope=openid profile email&response_type=code",
                oauthService.findAuthUrlByProviderCode("google"));
    }
}
