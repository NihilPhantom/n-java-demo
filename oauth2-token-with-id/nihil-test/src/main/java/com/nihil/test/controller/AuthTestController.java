package com.nihil.test.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthTestController {

    @ResponseBody
    @GetMapping("/testAuth")
    public Map<String, Object> index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                     @AuthenticationPrincipal OAuth2User oauth2User) {
        Map<String, Object> res = new HashMap<>();
        res.put("userName", oauth2User.getName());
        res.put("clientName", authorizedClient.getClientRegistration().getClientName());
        res.put("userAttributes", oauth2User);
        res.put("Principal", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return res;
    }
}
