package io.supertokens.pluginInterface.passwordless;

public class PasswordlessCode {
    public final String id;
    public final String deviceIdHash;
    public final String linkCodeHash;
    public final Long createdAt;

    public PasswordlessCode(String id, String deviceIdHash, String linkCodeHash, Long createdAt) {
        this.id = id;
        this.deviceIdHash = deviceIdHash;
        this.linkCodeHash = linkCodeHash;
        this.createdAt = createdAt;
    }
}