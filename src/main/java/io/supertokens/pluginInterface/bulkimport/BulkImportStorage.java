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

package io.supertokens.pluginInterface.bulkimport;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface BulkImportStorage extends NonAuthRecipeStorage {
    /**
    * Add users to the bulk_import_users table
    */
    void addBulkImportUsers(AppIdentifier appIdentifier, List<BulkImportUser> users)
            throws StorageQueryException,
            TenantOrAppNotFoundException;

    /**
    * Get users from the bulk_import_users table
    */
    List<BulkImportUser> getBulkImportUsers(AppIdentifier appIdentifier, @Nonnull Integer limit, @Nullable BULK_IMPORT_USER_STATUS status,
            @Nullable String bulkImportUserId, @Nullable Long createdAt) throws StorageQueryException;

    /**
    * Delete users by id from the bulk_import_users table
    */
    List<String> deleteBulkImportUsers(AppIdentifier appIdentifier, @Nonnull String[] bulkImportUserIds) throws StorageQueryException;

    /**
    * Returns the users from the bulk_import_users table for processing
    */
    List<BulkImportUser> getBulkImportUsersAndChangeStatusToProcessing(AppIdentifier appIdentifier, @Nonnull Integer limit) throws StorageQueryException;


    /**
    * Update the bulk_import_user's primary_user_id by bulk_import_user_id
    */
    void updateBulkImportUserPrimaryUserId(AppIdentifier appIdentifier, @Nonnull String bulkImportUserId, @Nonnull String primaryUserId) throws StorageQueryException;

    /**
    * Returns the count of users from the bulk_import_users table
    */
    long getBulkImportUsersCount(AppIdentifier appIdentifier, @Nullable BULK_IMPORT_USER_STATUS status) throws StorageQueryException;

    public enum BULK_IMPORT_USER_STATUS {
        NEW, PROCESSING, FAILED
    }
}
