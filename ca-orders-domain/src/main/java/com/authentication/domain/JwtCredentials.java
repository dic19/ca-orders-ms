package com.authentication.domain;

public class JwtCredentials extends Credentials {
    private final String accessToken;

    JwtCredentials(String accessToken) {
        super(CredentialsType.JWT, accessToken);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
