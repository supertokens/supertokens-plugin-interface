package io.supertokens.pluginInterface.totp;

public class TOTPDevice {
    public final String deviceName;
    public final String userId;
    public final String secretKey;
    public final int period;
    public final int skew;
    public final Boolean verified;

    public TOTPDevice(String deviceName, String userId, String secretKey, int period, int skew, boolean verified) {
        this.deviceName = deviceName;
        this.userId = userId;
        this.secretKey = secretKey;
        this.period = period;
        this.skew = skew;
        this.verified = verified;
    }
}
