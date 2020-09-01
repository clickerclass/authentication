package com.clickerclass.oauthserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuthenticationRequestModel {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
