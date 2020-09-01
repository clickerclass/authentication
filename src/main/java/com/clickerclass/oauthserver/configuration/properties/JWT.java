package com.clickerclass.oauthserver.configuration.properties;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JWT {
    private boolean enabled;
    private Map<String, Object> claimsStatics;
    private long timeExpiration;
    private String issuer;
    private String secret;
    private String audience;
}
