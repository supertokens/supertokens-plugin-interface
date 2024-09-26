package io.supertokens.pluginInterface.oauth;

public class OAuthLogoutChallenge {
    public final String challenge;
    public final String clientId;
    public final String postLogoutRedirectionUri;
    public final String sessionHandle;
    public final String state;
    public final long timeCreated;

    public OAuthLogoutChallenge(String challenge, String clientId, String postLogoutRedirectionUri, String sessionHandle, String state, long timeCreated) {
        this.challenge = challenge;
        this.clientId = clientId;
        this.postLogoutRedirectionUri = postLogoutRedirectionUri;
        this.sessionHandle = sessionHandle;
        this.state = state;
        this.timeCreated = timeCreated;
    }
}
