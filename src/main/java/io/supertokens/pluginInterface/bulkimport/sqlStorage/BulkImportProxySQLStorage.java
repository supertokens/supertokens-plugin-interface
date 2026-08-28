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

package io.supertokens.pluginInterface.bulkimport.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;

import java.sql.Savepoint;

/**
 * A storage bound to exactly one database connection, handed out by a {@link BulkImportProxyStoragePool}.
 *
 * <p>Every query routed through this storage (including nested {@code startTransaction} calls made by the
 * recipe code that bulk import reuses) runs on that single connection, and nothing is committed until the
 * caller says so via {@link #commitTransactionForBulkImportProxyStorage()}. That is what lets a worker claim
 * its {@code bulk_import_users} rows with {@code SELECT ... FOR UPDATE SKIP LOCKED}, import the users, and
 * delete (or error-mark) the claimed rows while the row locks are still held.
 *
 * <p>Savepoints let the worker undo a failed import <em>without</em> releasing the claim: a plain rollback
 * would drop the row locks and revert the rows to {@code NEW}, leaving a window in which another core
 * instance could re-claim them. {@code ROLLBACK TO SAVEPOINT} also recovers the connection from an aborted
 * state after a failed statement, so the error status can be written on the same connection.
 *
 * <p>Instances are <b>not</b> thread-safe: one worker thread, one proxy storage.
 */
public interface BulkImportProxySQLStorage extends BulkImportSQLStorage {

    /**
     * Creates a savepoint inside the currently open transaction. Rolling back to it undoes everything done
     * after this point but keeps the transaction (and every row lock taken before it) alive.
     */
    Savepoint createSavepointForBulkImportProxyStorage() throws StorageQueryException;

    /**
     * Undoes all work done after {@code savepoint} while keeping the transaction open. The savepoint itself
     * remains valid and can be rolled back to again.
     */
    void rollbackToSavepointForBulkImportProxyStorage(Savepoint savepoint) throws StorageQueryException;

    /**
     * Discards {@code savepoint} (the work done after it is kept). Optional; committing the transaction
     * releases all savepoints anyway.
     */
    void releaseSavepointForBulkImportProxyStorage(Savepoint savepoint) throws StorageQueryException;
}
