package io.supertokens.pluginInterface;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

import java.util.Map;

public interface ActiveUsersStorage extends NonAuthRecipeStorage {
    /* Update the last active time of a user to now */
    void updateLastActive(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    /* Count the number of users who did some activity after given timestamp */
    int countUsersActiveSince(AppIdentifier appIdentifier, long time) throws StorageQueryException;

    /*
     * Bucket users by how many whole days ago they were last active, counting only activity at or
     * after sinceTime. Key = days ago (0 = within the last 24h), value = number of users in that
     * bucket. Callers that need "active since N days ago" series values take the running total of
     * buckets 0..N-1 - one query instead of one per threshold.
     *
     * Rows with last_active_time > now (clock skew) are not expected to occur; implementations may
     * return them under negative keys rather than clamping to bucket 0, and callers must ignore any
     * keys outside the 0..N-1 range they consume.
     */
    Map<Integer, Integer> countUsersActiveSinceGroupedByDay(AppIdentifier appIdentifier, long sinceTime, long now)
            throws StorageQueryException;

    int countUsersThatHaveMoreThanOneLoginMethodAndActiveSince(AppIdentifier appIdentifier, long sinceTime)
            throws StorageQueryException;

    int countUsersThatHaveMoreThanOneLoginMethodOrTOTPEnabledAndActiveSince(AppIdentifier appIdentifier, long timestamp)
            throws StorageQueryException;
}
