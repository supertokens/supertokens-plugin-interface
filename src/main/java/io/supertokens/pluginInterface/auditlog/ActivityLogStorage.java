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
     * entirely older than the retention window. Storages that do not support partitioning (e.g. the
     * in-memory store) implement this as a no-op.
     */
    void maintainActivityLogPartitions() throws StorageQueryException;
}
