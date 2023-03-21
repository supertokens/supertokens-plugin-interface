package io.supertokens.pluginInterface;

public interface ActiveUsersStorage extends Storage {
    /* Update the last active time of a user to now */
    void updateLastActive(String userId);

    /* Count the number of users who did some activity after given timestamp */
    int countUsersActiveSince(long time);
}
