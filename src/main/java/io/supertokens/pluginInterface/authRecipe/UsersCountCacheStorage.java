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

import javax.annotation.Nullable;

import io.supertokens.pluginInterface.RECIPE_ID;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

/**
 * Optional storage capability: user counts that are computed once and shared by every core using this database,
 * instead of each core running its own full count.
 * <p>
 * A count stored by an earlier call is returned if it was computed less than {@code maxAgeMs} ago. Otherwise the
 * count is computed and stored. Computing is coordinated through the database, so concurrent callers, including
 * ones on other cores, wait for a single count and then return its stored value. The result carries the time the
 * count started and whether this call ran it.
 * <p>
 * {@code includeRecipeIds} (nullable) restricts the count to users with a login method of those recipes, as in
 * {@link AuthRecipeStorage#getUsersCount}; each recipe filter has its own stored count.
 * <p>
 * A failed count throws {@link StorageQueryException}. After a failure, counts of the same scope are refused for a
 * while (on every core) with a {@code StorageQueryException} caused by
 * {@link io.supertokens.pluginInterface.authRecipe.exceptions.UsersCountBackoffException}, instead of counting again
 * straight away.
 */
public interface UsersCountCacheStorage extends Storage {

    /**
     * The same value as {@link AuthRecipeStorage#getUsersCount(AppIdentifier, RECIPE_ID[])}: the number of distinct
     * users in the app on this storage.
     */
    UsersCountSnapshot getOrComputeAppUsersCount(AppIdentifier appIdentifier, @Nullable RECIPE_ID[] includeRecipeIds,
                                                 long maxAgeMs) throws StorageQueryException;

    /**
     * The same value as {@link AuthRecipeStorage#getUsersCount(TenantIdentifier, RECIPE_ID[])}: the number of
     * distinct users in the tenant.
     */
    UsersCountSnapshot getOrComputeTenantUsersCount(TenantIdentifier tenantIdentifier,
                                                    @Nullable RECIPE_ID[] includeRecipeIds, long maxAgeMs)
            throws StorageQueryException;

    /**
     * The last app count stored by {@link #getOrComputeAppUsersCount}, whatever its age, or null if there is none.
     * Never counts and never waits for a count.
     */
    @Nullable
    UsersCountSnapshot getStoredAppUsersCount(AppIdentifier appIdentifier, @Nullable RECIPE_ID[] includeRecipeIds)
            throws StorageQueryException;

    /**
     * The last tenant count stored by {@link #getOrComputeTenantUsersCount}, whatever its age, or null if there is
     * none. Never counts and never waits for a count.
     */
    @Nullable
    UsersCountSnapshot getStoredTenantUsersCount(TenantIdentifier tenantIdentifier,
                                                 @Nullable RECIPE_ID[] includeRecipeIds) throws StorageQueryException;
}
