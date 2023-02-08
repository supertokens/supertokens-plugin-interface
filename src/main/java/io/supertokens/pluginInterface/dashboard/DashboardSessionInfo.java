package io.supertokens.pluginInterface.dashboard;

public class DashboardSessionInfo {
    public String userId;
    public String sessionId;
    public long timeCreated;
    public long expiry;

    public DashboardSessionInfo(String userId, String sessionId, long timeCreated, long expiry) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.timeCreated = timeCreated;
        this.expiry = expiry;
    }

    public boolean equals(Object other) {
        if (other instanceof DashboardSessionInfo) {
            DashboardSessionInfo otherSessionInfo = (DashboardSessionInfo) other;
            return otherSessionInfo.userId.equals(this.userId) && otherSessionInfo.timeCreated == this.timeCreated
                    && otherSessionInfo.sessionId.equals(this.sessionId) && otherSessionInfo.expiry == this.expiry;
        }
        return false;
    }
}