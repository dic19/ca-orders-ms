package com.authentication.domain;

public abstract class Credentials {
    private final CredentialsType type;
    private final String value;

    protected Credentials(CredentialsType type, String value) {
        this.type = type;
        this.value = value;
    }

    public CredentialsType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {}

        public ApiKeyCredentials apiKeyCredentials(String apiKey) {
            return new ApiKeyCredentials(apiKey);
        }

        public JwtCredentials jwtCredentials(String accessToken) {
            return new JwtCredentials(accessToken);
        }

        public NoopCredentials noop() {
            return new NoopCredentials();
        }
    }
}
