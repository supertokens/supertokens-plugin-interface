package io.supertokens.pluginInterface.totp;

public class TOTPUsedCode {
    public final String userId;
    public final String code;
    public final boolean isValidCode;
    public final long expiryTime;

    public TOTPUsedCode(String userId, String code, Boolean isValidCode, long expiryTime) {
        this.userId = userId;
        this.code = code;
        this.isValidCode = isValidCode;
        this.expiryTime = expiryTime;
    }
}
