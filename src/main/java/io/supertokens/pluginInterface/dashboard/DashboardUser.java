package io.supertokens.pluginInterface.dashboard;

public class DashboardUser {
    public final String email;
    public String id;
    public long timeJoined;

    // using transient, we tell Gson not to include this when creating a JSON
    public transient final String passwordHash;

    public DashboardUser(String id, String email, String passwordHash, long timeJoined) {
        this.id = id;
        this.timeJoined = timeJoined;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public boolean equals(Object other) {
        if (other instanceof DashboardUser) {
            DashboardUser otherUserInfo = (DashboardUser) other;
            return otherUserInfo.email.equals(this.email) && otherUserInfo.passwordHash.equals(this.passwordHash)
                    && otherUserInfo.id.equals(this.id) && otherUserInfo.timeJoined == this.timeJoined;
        }
        return false;
    }
}
