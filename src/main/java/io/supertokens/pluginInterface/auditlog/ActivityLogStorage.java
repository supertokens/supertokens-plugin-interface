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
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

import java.util.List;
import java.util.Set;

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

    /**
     * Reads a bounded, app-scoped window of activity-log events so callers can fold them in Java rather
     * than in the database. Returns the events of {@code appIdentifier} (across all of its tenants, with
     * each row's {@code tenantId} preserved) whose {@code eventType} is in {@code eventTypes} and whose
     * {@code createdAt} lies in the half-open interval {@code (fromExclusiveMillis, toInclusiveMillis]},
     * ordered by {@code createdAt} ascending, capped at {@code limit} rows — i.e. the oldest {@code limit}
     * rows of the window. Tie order among rows with equal {@code createdAt} is unspecified.
     * <p>
     * {@code limit} is applied by the storage layer (in the query), never after materialising the window,
     * so a caller can pass {@code cap + 1} to detect an over-cap window without reading all of it.
     * <p>
     * Each returned {@link AuditLogEvent#payload} is its JSON text — a storage that keeps the payload as
     * native JSON returns the serialised form; a null payload stays null.
     * <p>
     * This is a plain auto-committed read (no transaction connection), living next to
     * {@link #hasUnfoldedActivitySince} so every activity-log storage implements it.
     *
     * @param appIdentifier      the app whose events (across all its tenants) are read
     * @param eventTypes         the {@code eventType} values to include; must be non-empty
     * @param fromExclusiveMillis lower bound on {@code createdAt}, exclusive
     * @param toInclusiveMillis   upper bound on {@code createdAt}, inclusive
     * @param limit              the maximum number of (oldest-first) rows to return; must be {@code > 0}
     * @return the matching events, oldest first, at most {@code limit} of them
     * @throws IllegalArgumentException if {@code limit <= 0} or {@code eventTypes} is empty — an
     *                                  unfiltered window read is never intended
     */
    List<AuditLogEvent> getActivityLogEntriesForApp(AppIdentifier appIdentifier, Set<String> eventTypes,
                                                    long fromExclusiveMillis, long toInclusiveMillis, int limit)
            throws StorageQueryException;
}
