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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

public interface BulkImportStorage extends NonAuthRecipeStorage {
    /**
     * Add users to the bulk_import_users table
     */
    void addBulkImportUsers(AppIdentifier appIdentifier, List<BulkImportUser> users)
            throws StorageQueryException,
            TenantOrAppNotFoundException,
            io.supertokens.pluginInterface.bulkimport.exceptions.DuplicateUserIdException;

    /**
     * Get users from the bulk_import_users table
     */
    List<BulkImportUserInfo> getBulkImportUsers(AppIdentifier appIdentifier, @Nonnull Integer limit, @Nullable BulkImportUserStatus status,
            @Nullable String bulkImportUserId, @Nullable Long createdAt) throws StorageQueryException;

    /**
     * Delete users by id from the bulk_import_users table
     */
    List<String> deleteBulkImportUsers(AppIdentifier appIdentifier, @Nonnull String[] bulkImportUserIds) throws StorageQueryException;

    public enum BulkImportUserStatus {
        NEW, PROCESSING, FAILED
    }
}
