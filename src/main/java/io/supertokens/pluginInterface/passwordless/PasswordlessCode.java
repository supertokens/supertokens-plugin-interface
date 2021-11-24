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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((deviceIdHash == null) ? 0 : deviceIdHash.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((linkCodeHash == null) ? 0 : linkCodeHash.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PasswordlessCode)) {
            return false;
        }
        PasswordlessCode other = (PasswordlessCode) obj;
        if (createdAt == null) {
            if (other.createdAt != null) {
                return false;
            }
        } else if (!createdAt.equals(other.createdAt)) {
            return false;
        }
        if (deviceIdHash == null) {
            if (other.deviceIdHash != null) {
                return false;
            }
        } else if (!deviceIdHash.equals(other.deviceIdHash)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (linkCodeHash == null) {
            if (other.linkCodeHash != null) {
                return false;
            }
        } else if (!linkCodeHash.equals(other.linkCodeHash)) {
            return false;
        }
        return true;
    }
}