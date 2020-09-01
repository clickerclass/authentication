package com.clickerclass.oauthserver.handler;

import com.clickerclass.oauthserver.model.AuthenticationRequestModel;
import com.clickerclass.oauthserver.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OAuthHandler {
    private final Validator validator;
    private final OAuthService oAuthService;

    public OAuthHandler(@Autowired Validator validator, @Autowired OAuthService oAuthService) {
        this.validator = validator;
        this.oAuthService = oAuthService;
    }

    public Mono<ServerResponse> authentication(ServerRequest request) {
        return request.bodyToMono(AuthenticationRequestModel.class).flatMap(authentication -> {
            Errors errors = new BeanPropertyBindingResult(authentication, AuthenticationRequestModel.class.getName());
            validator.validate(authentication, errors);
            if (errors.hasErrors()) {
                return ServerResponse.badRequest().body(BodyInserters.fromValue(errors.getAllErrors()));
            }
            return oAuthService.authentication(authentication);
        });
    }
}
