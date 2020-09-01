package com.clickerclass.oauthserver.service;

import com.clickerclass.oauthserver.model.AuthenticationRequestModel;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface OAuthService {
    Mono<ServerResponse> authentication(AuthenticationRequestModel authenticationModel);
}
