package io.supertokens.pluginInterface;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public interface ActiveUsersStorage extends Storage {
    /* Update the last active time of a user to now */
    void updateLastActive(String userId) throws StorageQueryException;

    /* Count the number of users who did some activity after given timestamp */
    int countUsersActiveSince(long time) throws StorageQueryException;
}
