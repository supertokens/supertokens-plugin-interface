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

package io.supertokens.pluginInterface.auditlog;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface ActivityLogSQLStorage extends ActivityLogStorage, SQLStorage {

    /**
     * Writes an activity-log entry on the caller's transaction connection, so it commits or rolls back
     * atomically with the surrounding mutation. Same insert as {@link #createActivityLogEntry}, but on
     * the passed connection rather than an auto-committed one.
     */
    void createActivityLogEntry_Transaction(TransactionConnection con, TenantIdentifier tenantIdentifier,
                                            AuditLogEvent event) throws StorageQueryException;

    /**
     * Runs an audited mutation atomically with its audit trail: executes {@code logic} inside a
     * transaction, writes the {@link AuditedResult}'s events on the same connection via
     * {@link #createActivityLogEntry_Transaction}, then commits. If the logic throws, nothing is
     * committed — the mutation and its audit events land together or not at all. A logic that returns
     * {@link AuditedResult#withoutAudit} commits the mutation with no audit event.
     * <p>
     * Events are written under the given app, at each event's own tenant ({@link AuditLogEvent#tenantId},
     * defaulting to the public tenant when null).
     */
    default <T> T startAuditedTransaction(AppIdentifier appIdentifier, AuditableTransactionLogic<T> logic)
            throws StorageQueryException, StorageTransactionLogicException {
        return startTransaction(con -> {
            AuditedResult<T> audited = logic.mainLogic(con);
            for (AuditLogEvent event : audited.getEvents()) {
                TenantIdentifier tenantIdentifier = new TenantIdentifier(
                        appIdentifier.getConnectionUriDomain(), appIdentifier.getAppId(), event.tenantId);
                createActivityLogEntry_Transaction(con, tenantIdentifier, event);
            }
            commitTransaction(con);
            return audited.getResult();
        });
    }
}
