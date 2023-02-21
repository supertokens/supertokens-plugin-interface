package io.supertokens.pluginInterface.totp;

import javax.annotation.Nullable;

public class TOTPUsedCode {
    public final String userId;
    public final @Nullable String deviceName;
    public final String code;
    public final boolean isValidCode;
    public final long expiryTime;

    public TOTPUsedCode(String userId, @Nullable String deviceName, String code, Boolean isValidCode, long expiryTime) {
        this.userId = userId;
        this.deviceName = deviceName;
        this.code = code;
        this.isValidCode = isValidCode;
        this.expiryTime = expiryTime;
    }
}
