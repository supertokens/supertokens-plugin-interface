/*
 *    Copyright (c) 2026, VRAI Labs and/or its affiliates. All rights reserved.
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
 *
 */

package io.supertokens.pluginInterface.authRecipe;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;

/**
 * Optional storage capability: an app's user count that is computed once and shared by every core using this
 * database, instead of each core running its own full count.
 */
public interface AppUsersCountCacheStorage extends Storage {

    /**
     * Returns the same value as {@link AuthRecipeStorage#getUsersCount(AppIdentifier, io.supertokens.pluginInterface.RECIPE_ID[])}
     * with no recipe filter: the number of distinct users in the app on this storage.
     * <p>
     * A value stored by an earlier call is returned if it was computed less than {@code maxAgeMs} ago. Otherwise
     * the count is computed and stored. Computing is coordinated through the database, so concurrent callers,
     * including ones on other cores, wait for a single count and then return its stored value.
     */
    long getOrComputeAppUsersCount(AppIdentifier appIdentifier, long maxAgeMs) throws StorageQueryException;
}
