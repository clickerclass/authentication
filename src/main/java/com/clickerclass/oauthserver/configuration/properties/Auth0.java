package com.clickerclass.oauthserver.configuration.properties;

import lombok.Data;

import java.util.Map;

@Data
public class Auth0 {
    private Map<String, String> authorization;
    private Map<String, String> validations;
    private boolean enabled;
}
