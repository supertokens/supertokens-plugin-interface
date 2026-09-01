/*
 *    Copyright (c) 2023, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface ActiveUsersSQLStorage extends ActiveUsersStorage, SQLStorage {
    /* Delete a user from active users table */
    void deleteUserActive_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Derives {@code user_last_active} from the activity log for the window {@code [windowStartMillis, now]},
     * on the caller's transaction connection. Folds each user's most recent activity-log entry whose {@code
     * event_type} is in {@link io.supertokens.pluginInterface.auditlog.RollupEventTypes#FOLD_SET} into the
     * projection (monotonically: an existing later timestamp is never lowered) — the synthetic {@code
     * user_last_active} event type this method once folded has been retired, so an implementation must fold
     * the concrete event types in {@code FOLD_SET}, not {@code event_type = 'user_last_active'} (which now
     * matches nothing). Then reconciles rows for users linked away within the same window: an {@code
     * account_linking} event credits the surviving primary, and the linked-away recipe user's stale row is
     * dropped. Storage-wide: no app identifier — one pass over the whole storage.
     */
    void rollupLastActiveFromActivityLog_Transaction(TransactionConnection con, long windowStartMillis)
            throws StorageQueryException;
}
