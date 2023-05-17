package io.supertokens.pluginInterface.totp;

public class TOTPDevice {
    public final String deviceName;
    public final String userId;
    public final String secretKey;
    public final int period;
    public final int skew;
    public final boolean verified;

    public TOTPDevice(String userId, String deviceName, String secretKey, int period,
                      int skew, boolean verified) {
        this.userId = userId;
        this.deviceName = deviceName;
        this.secretKey = secretKey;
        this.period = period;
        this.skew = skew;
        this.verified = verified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TOTPDevice)) {
            return false;
        }
        TOTPDevice other = (TOTPDevice) obj;
        return this.userId.equals(other.userId) &&
                this.deviceName.equals(other.deviceName)
                && this.secretKey.equals(other.secretKey) && this.period == other.period && this.skew == other.skew
                && this.verified == other.verified;
    }
}
