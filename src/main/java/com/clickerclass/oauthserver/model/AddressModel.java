package com.clickerclass.oauthserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddressModel {
    private String country;
    private String state;
    private String city;
    private String description;
}
