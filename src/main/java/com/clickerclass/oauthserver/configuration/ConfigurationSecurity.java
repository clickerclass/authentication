package com.clickerclass.oauthserver.configuration;

import com.clickerclass.oauthserver.configuration.properties.Auth0;
import com.clickerclass.oauthserver.configuration.properties.JWT;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gema.security")
@ConditionalOnProperty(
        prefix = "gema.security",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false)
@Data
public class ConfigurationSecurity {
    private Auth0 auth0;
    private JWT jwt;
    private String uri;
    private String path;
    private boolean enabled;
}
