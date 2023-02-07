package io.supertokens.pluginInterface.dashboard;

public class DashboardSessionInfo {
    public String userId;
    public String sessionId;
    public long timeCreated;

    public DashboardSessionInfo(String userId, String sessionId, long timeCreated) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.timeCreated = timeCreated;
    }

    public boolean equals(Object other) {
        if (other instanceof DashboardSessionInfo) {
            DashboardSessionInfo otherSessionInfo = (DashboardSessionInfo) other;
            return otherSessionInfo.userId.equals(this.userId) && otherSessionInfo.timeCreated == this.timeCreated
                    && otherSessionInfo.sessionId.equals(this.sessionId);
        }
        return false;
    }
}