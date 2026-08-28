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

/**
 * A dedicated, bounded connection pool used only by bulk import, opened against the same database as the
 * storage it was created from (see {@link BulkImportSQLStorage#openBulkImportProxyStoragePool(int)}).
 *
 * <p>Bulk import deliberately does not borrow from the live connection pool that serves API traffic: a
 * worker holds its connection (and the row locks on the claimed {@code bulk_import_users} rows) for the
 * whole duration of a chunk import, which would otherwise starve requests. The pool is sized by the caller,
 * normally to the import parallelism, so the number of server connections an import can add is known up
 * front. It is created only when there is something to import and closed when the run ends.
 *
 * <p>{@link #createProxyStorage()} hands out one single-connection storage per worker; all proxies share
 * this pool. Closing the pool closes every proxy storage created from it.
 */
public interface BulkImportProxyStoragePool extends AutoCloseable {

    /**
     * Creates a storage bound to one connection from this pool. The connection is taken lazily on first use
     * and returned when {@link BulkImportProxySQLStorage#closeConnectionForBulkImportProxyStorage()} is
     * called or the pool is closed. Callers must not share the returned storage between threads.
     */
    BulkImportProxySQLStorage createProxyStorage() throws StorageQueryException;

    /**
     * Closes every proxy storage created from this pool (rolling back anything left uncommitted) and
     * releases all of the pool's connections.
     */
    @Override
    void close() throws StorageQueryException;
}
