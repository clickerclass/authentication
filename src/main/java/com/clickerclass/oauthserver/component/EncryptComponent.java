package com.clickerclass.oauthserver.component;

import org.bouncycastle.util.Strings;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptComponent {
    public String encrypting(String value) {
        String valueEncrypt = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            valueEncrypt = Strings.fromByteArray(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return valueEncrypt;
    }
}
