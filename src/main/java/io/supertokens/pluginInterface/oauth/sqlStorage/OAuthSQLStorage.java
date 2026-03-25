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

package io.supertokens.pluginInterface.oauth.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.oauth.OAuthStorage;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

/**
 * SQL-specific OAuth storage methods that require transactional access.
 *
 * <p>These methods are used to implement the DB-level mutex for non-rotating
 * refresh token exchange: a {@code SELECT ... FOR UPDATE} on the oauth_sessions
 * row keeps all other instances waiting until the Hydra round-trip and the
 * subsequent mapping update are committed together.
 */
public interface OAuthSQLStorage extends OAuthStorage, SQLStorage {

    /**
     * Reads {@code internal_refresh_token} for the given {@code externalRefreshToken}
     * inside an open transaction, acquiring a row-level exclusive lock
     * ({@code SELECT ... FOR UPDATE}).  The lock is held until the caller
     * either commits or rolls back the surrounding transaction.
     *
     * @return the internal refresh token, or {@code null} if no mapping row exists
     *         (caller should fall back to using the external token as-is)
     */
    String getRefreshTokenMappingForUpdate(AppIdentifier appIdentifier,
                                           TransactionConnection con,
                                           String externalRefreshToken)
            throws StorageQueryException;

    /**
     * Updates {@code internal_refresh_token}, {@code session_handle}, {@code jti},
     * and {@code exp} for the row identified by {@code externalRefreshToken}, inside
     * the same open transaction that previously called
     * {@link #getRefreshTokenMappingForUpdate}.
     *
     * <p>The {@code external_refresh_token} column itself is intentionally left
     * unchanged — the client keeps using the same external token indefinitely.
     */
    void updateOAuthSessionInternal(AppIdentifier appIdentifier,
                                    TransactionConnection con,
                                    String externalRefreshToken,
                                    String newInternalRefreshToken,
                                    String sessionHandle,
                                    String jti,
                                    long exp)
            throws StorageQueryException;
}
