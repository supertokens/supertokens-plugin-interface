package io.supertokens.pluginInterface.dashboard;

public class DashboardUser {
    public final String email;
    public String userId;
    public long timeJoined;

    // using transient, we tell Gson not to include this when creating a JSON
    public transient final String passwordHash;

    public DashboardUser(String userId, String email, String passwordHash, long timeJoined) {
        this.userId = userId;
        this.timeJoined = timeJoined;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public boolean equals(Object other) {
        if (other instanceof DashboardUser) {
            DashboardUser otherUserInfo = (DashboardUser) other;
            return otherUserInfo.email.equals(this.email) && otherUserInfo.passwordHash.equals(this.passwordHash)
                    && otherUserInfo.userId.equals(this.userId) && otherUserInfo.timeJoined == this.timeJoined;
        }
        return false;
    }
}
