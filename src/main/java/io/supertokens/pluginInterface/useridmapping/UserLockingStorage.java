/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.pluginInterface.useridmapping;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Storage interface for acquiring user locks.
 * Implementations must use SELECT ... FOR UPDATE or equivalent.
 */
public interface UserLockingStorage {

    /**
     * Acquires a lock on a single user (and their primary user if linked).
     *
     * @param appIdentifier The app context
     * @param con The transaction connection (lock is held for transaction duration)
     * @param userId The user ID to lock
     * @return LockedUser instance proving the lock was acquired
     * @throws UserNotFoundForLockingException if user does not exist
     * @throws StorageQueryException on database errors
     */
    @Nonnull
    LockedUser lockUser(AppIdentifier appIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException, UserNotFoundForLockingException;

    /**
     * Acquires locks on multiple users (and their primary users if linked).
     * Users are locked in a consistent order to prevent deadlocks.
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param userIds List of user IDs to lock
     * @return List of LockedUser instances in the same order as input
     * @throws UserNotFoundForLockingException if any user does not exist
     * @throws StorageQueryException on database errors
     */
    @Nonnull
    List<LockedUser> lockUsers(AppIdentifier appIdentifier, TransactionConnection con, List<String> userIds)
            throws StorageQueryException, UserNotFoundForLockingException;

    /**
     * Acquires locks on two users - convenience method for linking operations.
     * Handles ordering to prevent deadlocks.
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param recipeUserId The recipe user ID
     * @param primaryUserId The primary user ID
     * @return LockedUserPair containing both locked users
     * @throws UserNotFoundForLockingException if either user does not exist
     * @throws StorageQueryException on database errors
     */
    @Nonnull
    LockedUserPair lockUsersForLinking(AppIdentifier appIdentifier, TransactionConnection con,
                                        String recipeUserId, String primaryUserId)
            throws StorageQueryException, UserNotFoundForLockingException;
}
