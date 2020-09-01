package com.clickerclass.oauthserver.filter;

import com.clickerclass.oauthserver.configuration.ConfigurationSecurity;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Objects;


@Slf4j
public class OAuthFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private final ConfigurationSecurity configurationSecurity;

    public OAuthFilter(ConfigurationSecurity configurationSecurity) {
        this.configurationSecurity = configurationSecurity;
    }

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        String authorization = request.headers().firstHeader("Authorization");
        if (Objects.nonNull(authorization) && !authorization.isEmpty()) {
            String access[] = Strings.fromByteArray(Base64.getDecoder().decode(authorization.split(" ")[1])).split(":");
            if (configurationSecurity.getAuth0().getAuthorization().containsKey(access[0]) && access.length == 2) {
                String password = configurationSecurity.getAuth0().getAuthorization().get(access[0]);
                if (password.equals(access[1])) {
                    return next.handle(request);
                }
            }
        }
        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
    }
}
