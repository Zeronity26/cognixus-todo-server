package com.cognixus.todo.controller;

import com.cognixus.todo.model.dto.OauthTokenDto;
import com.cognixus.todo.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OauthController {
    private final OauthService oauthService;

    @GetMapping("/{providerCode}/login")
    public void redirectLogin(@PathVariable String providerCode, HttpServletResponse response) throws IOException {
        String authUrl = oauthService.findAuthUrlByProviderCode(providerCode);
        response.sendRedirect(authUrl);
    }

    @GetMapping("/{providerCode}/callback")
    @ResponseStatus(HttpStatus.OK)
    public OauthTokenDto loginCallback(@PathVariable String providerCode, @RequestParam("code") String code) {
        return oauthService.findAccessTokenByProviderCode(providerCode,code);
    }
}
