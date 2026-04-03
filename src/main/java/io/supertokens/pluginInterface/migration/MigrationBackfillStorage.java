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
 */

package io.supertokens.pluginInterface.migration;

import io.supertokens.pluginInterface.MigrationMode;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;

/**
 * Storage interface for migration backfill operations.
 * Used to populate reservation tables (recipe_user_account_infos, recipe_user_tenants,
 * primary_user_tenants) and time_joined on app_id_to_user_id for users that were
 * created before the migration was enabled.
 *
 * This interface can be removed once the migration is complete and the old tables
 * (all_auth_recipe_users, *_user_to_tenant) are dropped.
 */
public interface MigrationBackfillStorage extends Storage {

    /**
     * Returns the current migration mode from the storage's configuration.
     */
    MigrationMode getMigrationMode();

    /**
     * Returns the count of users that still need backfilling.
     * A user needs backfilling when their time_joined is 0 in app_id_to_user_id,
     * which indicates they were created before the migration columns were added.
     *
     * @param appIdentifier The app context
     * @return Count of users with time_joined = 0
     */
    int getBackfillPendingUsersCount(AppIdentifier appIdentifier) throws StorageQueryException;

    /**
     * Backfills a batch of users by populating their data in the new tables.
     * For each user in the batch, this method:
     * 1. Locks the user row (SELECT FOR UPDATE on app_id_to_user_id)
     * 2. Copies time_joined from all_auth_recipe_users
     * 3. Populates recipe_user_account_infos from recipe-specific tables
     * 4. Populates recipe_user_tenants from all_auth_recipe_users + account_infos
     * 5. Populates primary_user_tenants for linked/primary users
     *
     * All operations use ON CONFLICT DO NOTHING for idempotency.
     *
     * @param appIdentifier The app context
     * @param batchSize Maximum number of users to process in this batch
     * @return Number of users actually processed (less than batchSize means done)
     */
    int backfillUsersBatch(AppIdentifier appIdentifier, int batchSize) throws StorageQueryException;

    /**
     * Verifies backfill completeness by checking for users that exist in
     * app_id_to_user_id but are missing from recipe_user_account_infos.
     *
     * @param appIdentifier The app context
     * @return Count of users missing from reservation tables (0 = all consistent)
     */
    int verifyBackfillCompleteness(AppIdentifier appIdentifier) throws StorageQueryException;
}
