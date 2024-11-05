package io.supertokens.pluginInterface.oauth;

public class OAuthClient {
    public final String clientId;
    public final String clientSecret;
    public final boolean isClientCredentialsOnly;
    public final boolean enableRefreshTokenRotation;

    public OAuthClient(String clientId, String clientSecret, boolean isClientCredentialsOnly, boolean enableRefreshTokenRotation) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.isClientCredentialsOnly = isClientCredentialsOnly;
        this.enableRefreshTokenRotation = enableRefreshTokenRotation;
    }
}
