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
import io.supertokens.pluginInterface.bulkimport.BulkImportUser;
import io.supertokens.pluginInterface.exceptions.DbInitException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public interface BulkImportSQLStorage extends BulkImportStorage, SQLStorage {

    /**
    * Update the status of the users in the bulk_import_users table
    */
    void updateBulkImportUserStatus_Transaction(AppIdentifier appIdentifier,
            TransactionConnection con, @Nonnull String bulkImportUserId, @Nonnull BULK_IMPORT_USER_STATUS status, @Nullable String errorMessage) throws StorageQueryException;

    void updateMultipleBulkImportUsersStatusToError_Transaction(AppIdentifier appIdentifier,
            TransactionConnection con, @Nonnull Map<String, String> bulkImportUserIdToErrorMessage) throws StorageQueryException;

    /**
     * Selects and marks users as PROCESSING within an already-open transaction, so the FOR UPDATE row locks
     * are held by the caller's transaction for the full duration of processing.
     */
    List<BulkImportUser> getBulkImportUsersAndChangeStatusToProcessing_Transaction(
            AppIdentifier appIdentifier, @Nonnull Integer limit, TransactionConnection con) throws StorageQueryException;

    /**
     * Deletes bulk-import users within an already-open transaction, keeping the row locks alive until commit.
     */
    void deleteBulkImportUsers_Transaction(
            AppIdentifier appIdentifier, @Nonnull String[] bulkImportUserIds, TransactionConnection con) throws StorageQueryException;

    /**
     * Opens a dedicated connection pool of at most {@code maxConnections} connections against this storage's
     * database, for use by bulk import only. See {@link BulkImportProxyStoragePool} for the rationale.
     * The caller owns the returned pool and must {@link BulkImportProxyStoragePool#close() close} it.
     */
    BulkImportProxyStoragePool openBulkImportProxyStoragePool(int maxConnections) throws DbInitException;
}
