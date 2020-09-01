package com.clickerclass.oauthserver.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.clickerclass.oauthserver.Exception.RestClientException;
import com.clickerclass.oauthserver.component.EncryptComponent;
import com.clickerclass.oauthserver.component.UserComponent;
import com.clickerclass.oauthserver.configuration.ConfigurationSecurity;
import com.clickerclass.oauthserver.model.AuthenticationRequestModel;
import com.clickerclass.oauthserver.model.AuthenticationResponseModel;
import com.clickerclass.oauthserver.model.UserModel;
import com.clickerclass.oauthserver.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class OAuthServiceImp implements OAuthService {
    private final UserComponent userComponent;
    private final EncryptComponent encryptComponent;
    private final ConfigurationSecurity configurationSecurity;

    public OAuthServiceImp(@Autowired UserComponent userComponent, @Autowired EncryptComponent encryptComponent, @Autowired ConfigurationSecurity configurationSecurity) {
        this.userComponent = userComponent;
        this.encryptComponent = encryptComponent;
        this.configurationSecurity = configurationSecurity;
    }

    @Override
    public Mono<ServerResponse> authentication(AuthenticationRequestModel authenticationModel) {
        return userComponent.callUserService(authenticationModel.getEmail()).flatMap(userModel -> {
            if (userModel.getPassword().equals(encryptComponent.encrypting(authenticationModel.getPassword()))) {
                return ServerResponse.ok().body(BodyInserters.fromValue(generateToken(userModel)));
            } else {
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).onErrorResume(RestClientException.class, e -> ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }

    private AuthenticationResponseModel generateToken(UserModel userModel) {
        AuthenticationResponseModel authenticationResponseModel = null;
        try {
            long expirationTime = System.currentTimeMillis() + configurationSecurity.getJwt().getTimeExpiration();
            Algorithm algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(configurationSecurity.getJwt().getSecret()));
            String token = JWT.create()
                    .withIssuer(configurationSecurity.getJwt().getIssuer())
                    .withHeader(configurationSecurity.getJwt().getClaimsStatics())
                    .withExpiresAt(new Date(expirationTime))
                    .withClaim("id", userModel.getId())
                    .withClaim("email", userModel.getEmail())
                    .withClaim("name", userModel.getName())
                    .withClaim("document", userModel.getDocument())
                    .withClaim("documentType", userModel.getDocumentType().name())
                    .withClaim("userType", userModel.getUserType().name())
                    .withAudience(configurationSecurity.getJwt().getAudience())
                    .withAudience()
                    .sign(algorithm);
            authenticationResponseModel = AuthenticationResponseModel.builder().token(token).creationDate(new Date()).expirationTime(expirationTime).build();
        } catch (JWTCreationException exception) {
            log.error(exception.getMessage());
        }
        return authenticationResponseModel;
    }
}
