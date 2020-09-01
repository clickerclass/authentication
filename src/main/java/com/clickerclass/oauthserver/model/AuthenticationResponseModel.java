package com.clickerclass.oauthserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AuthenticationResponseModel {
    private String token;
    private long expirationTime;
    private Date creationDate;
}
