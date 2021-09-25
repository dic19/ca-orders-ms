package com.authentication.domain;

public class ApiKeyCredentials extends Credentials {
    private final String apiKey;

    ApiKeyCredentials(String apiKey) {
        super(CredentialsType.API_KEY, apiKey);
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
