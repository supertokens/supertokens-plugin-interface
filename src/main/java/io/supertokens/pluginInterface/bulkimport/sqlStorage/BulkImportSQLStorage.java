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

package io.supertokens.pluginInterface.bulkimport.sqlStorage;

import io.supertokens.pluginInterface.bulkimport.BulkImportStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public interface BulkImportSQLStorage extends BulkImportStorage, SQLStorage {

    /**
    * Update the status of the users in the bulk_import_users table
    */
    void updateBulkImportUserStatus_Transaction(AppIdentifier appIdentifier,
            TransactionConnection con, @Nonnull String bulkImportUserId, @Nonnull BULK_IMPORT_USER_STATUS status, @Nullable String errorMessage) throws StorageQueryException;

    void updateMultipleBulkImportUsersStatusToError_Transaction(AppIdentifier appIdentifier,
            TransactionConnection con, @Nonnull Map<String, String> bulkImportUserIdToErrorMessage) throws StorageQueryException;
}
