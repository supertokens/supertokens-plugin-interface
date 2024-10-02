package io.supertokens.pluginInterface.oauth;

public enum OAuthRevokeTargetType {
    CLIENT_ID("client_id"),
    GID("gid"),
    JTI("jti"),
    SESSION_HANDLE("session_handle");

    private final String value;

    OAuthRevokeTargetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
