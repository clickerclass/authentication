package com.clickerclass.oauthserver.component;

import com.clickerclass.oauthserver.Exception.RestClientException;
import com.clickerclass.oauthserver.configuration.ConfigurationSecurity;
import com.clickerclass.oauthserver.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

@Component
public class UserComponent {
    private final ConfigurationSecurity configurationSecurity;

    public UserComponent(@Autowired ConfigurationSecurity configurationSecurity) {
        this.configurationSecurity = configurationSecurity;
    }

    public Mono<UserModel> callUserService(String email) {
        return instance().get()
                .uri(configurationSecurity.getPath().concat("?email=").concat(email))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(RestClientException::new))
                .bodyToMono(UserModel.class);
    }

    private WebClient instance() {
        return WebClient
                .builder()
                .baseUrl(configurationSecurity.getUri())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
