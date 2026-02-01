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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents a user that has been locked for the duration of a transaction.
 *
 * This type provides compile-time enforcement that a user lock has been acquired.
 * Methods that require exclusive access to a user should take LockedUser as a
 * parameter rather than a raw user ID string.
 *
 * LockedUser instances are only valid within the transaction that created them.
 * Do not store or pass them outside the transaction scope.
 */
public interface LockedUser {

    /**
     * Gets the recipe user ID (the user that was directly locked).
     */
    @Nonnull
    String getRecipeUserId();

    /**
     * Gets the recipe ID for this user.
     * The recipe ID identifies which authentication recipe was used
     * (e.g., "emailpassword", "thirdparty", "passwordless").
     */
    @Nonnull
    String getRecipeId();

    /**
     * Gets the primary user ID if this user is linked, null otherwise.
     * If this user IS a primary user, returns the same as getRecipeUserId().
     */
    @Nullable
    String getPrimaryUserId();

    /**
     * Returns true if this user is linked to a primary user (but is not itself the primary).
     */
    default boolean isLinked() {
        String primaryId = getPrimaryUserId();
        return primaryId != null && !primaryId.equals(getRecipeUserId());
    }

    /**
     * Returns true if this user is a primary user.
     */
    default boolean isPrimary() {
        String primaryId = getPrimaryUserId();
        return primaryId != null;
    }

    /**
     * Returns true if this lock is still valid for the given connection.
     * Checks that:
     * 1. The connection matches the one used to acquire the lock
     * 2. The connection is still open (transaction still active)
     *
     * @param connection The connection to validate against (must be the same connection
     *                   used when acquiring the lock)
     * @return true if the lock is valid for this connection
     */
    boolean isValidForConnection(Object connection);
}
