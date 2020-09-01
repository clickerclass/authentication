package com.clickerclass.oauthserver.model;

import com.clickerclass.oauthserver.enumeration.DocumentType;
import com.clickerclass.oauthserver.enumeration.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private String id;
    private String name;
    private String lastName;
    private String document;
    private String email;
    private DocumentType documentType;
    private UserType userType;
    private AddressModel address;
    private String phone;
    private String password;
    private Timestamp creationDate;

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", documentType=" + documentType +
                ", userType=" + userType +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}