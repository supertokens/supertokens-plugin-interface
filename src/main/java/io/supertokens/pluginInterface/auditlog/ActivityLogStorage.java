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

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

public interface ActivityLogStorage extends Storage {
    void createActivityLogEntry(TenantIdentifier tenantIdentifier, AuditLogEvent event) throws StorageQueryException;

    /**
     * Maintains the time-based partitioning of the activity_log table: pre-creates the partitions for
     * upcoming months (so inserts always have a partition to land in) and drops partitions whose data is
     * entirely older than {@code retentionDays}. Retention is supplied by the caller (from configuration)
     * rather than hardcoded in the storage layer. Storages that do not support partitioning (e.g. the
     * in-memory store) implement this as a no-op.
     */
    void maintainActivityLogPartitions(int retentionDays) throws StorageQueryException;

    /**
     * Cheap existence check for rollup-relevant activity-log rows newer than {@code sinceMillis}
     * (i.e. rows the last-active rollup would fold or reconcile). Lets the rollup cron skip work — and
     * avoid touching the connection pool — when there is nothing new to fold. Storage-wide: no app
     * predicate.
     */
    boolean hasUnfoldedActivitySince(long sinceMillis) throws StorageQueryException;
}
