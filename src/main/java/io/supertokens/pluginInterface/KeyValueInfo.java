package io.supertokens.pluginInterface;

public class KeyValueInfo {
    public String value;
    public long createdAtTime;

    public KeyValueInfo(String value, long createdAtTime) {
        this.value = value;
        this.createdAtTime = createdAtTime;
    }

    public KeyValueInfo(String value) {
        this.value = value;
        this.createdAtTime = System.currentTimeMillis();
    }
}
