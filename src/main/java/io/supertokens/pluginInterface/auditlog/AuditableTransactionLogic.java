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
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import java.sql.SQLException;

/**
 * The caller-supplied body of an audited transaction. It performs the mutation on the given
 * connection and returns an {@link AuditedResult} carrying the mutation's result and the audit
 * events describing it. The combinator {@code ActivityLogSQLStorage.startAuditedTransaction} then
 * writes those events on the same connection and commits, so the mutation and its audit trail are
 * atomic — either both land or neither does.
 */
@FunctionalInterface
public interface AuditableTransactionLogic<T> {
    AuditedResult<T> mainLogic(TransactionConnection con)
            throws StorageQueryException, StorageTransactionLogicException, TenantOrAppNotFoundException, SQLException;
}
