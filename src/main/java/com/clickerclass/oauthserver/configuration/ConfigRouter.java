package com.clickerclass.oauthserver.configuration;

import com.clickerclass.oauthserver.filter.OAuthFilter;
import com.clickerclass.oauthserver.handler.OAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@ConditionalOnProperty(
        prefix = "gema.security",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false)
public class ConfigRouter {
    private final ConfigurationSecurity configurationSecurity;

    public ConfigRouter(@Autowired ConfigurationSecurity configurationSecurity) {
        this.configurationSecurity = configurationSecurity;
    }

    @Bean
    public RouterFunction<ServerResponse> routes(OAuthHandler handler) {
        return RouterFunctions.route(POST("/api/authentication"), handler::authentication).filter(new OAuthFilter(configurationSecurity));
    }
}
