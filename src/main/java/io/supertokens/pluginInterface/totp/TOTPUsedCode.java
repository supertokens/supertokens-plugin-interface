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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TOTPUsedCode)) {
            return false;
        }
        TOTPUsedCode other = (TOTPUsedCode) obj;
        return this.userId.equals(other.userId) && this.code.equals(other.code)
                && this.isValidCode == other.isValidCode && this.expiryTime == other.expiryTime;
    }
}
