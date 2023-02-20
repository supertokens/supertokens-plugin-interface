package io.supertokens.pluginInterface.totp;

public class TOTPDevice {
    public final String deviceName;
    public final String userId;
    public final String secretKey;
    public final int period;
    public final int skew;
    public final boolean verified;

    public TOTPDevice(String userId, String deviceName, String secretKey, int period, int skew, boolean verified) {
        this.userId = userId;
        this.deviceName = deviceName;
        this.secretKey = secretKey;
        this.period = period;
        this.skew = skew;
        this.verified = verified;
    }
}
