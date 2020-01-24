package io.supertokens.pluginInterface.tokenInfo;

public class PastTokenInfo {

    public final String refreshTokenHash2;

    public final String sessionHandle;

    public final String parentRefreshTokenHash2;

    public final long createdTime;

    public PastTokenInfo(String refreshTokenHash2, String sessionHandle, String parentRefreshTokenHash2,
                         long createdTime) {
        this.refreshTokenHash2 = refreshTokenHash2;
        this.sessionHandle = sessionHandle;
        this.parentRefreshTokenHash2 = parentRefreshTokenHash2;
        this.createdTime = createdTime;
    }
}
