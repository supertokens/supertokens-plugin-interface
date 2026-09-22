/*
 *    Copyright (c) 2020, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.session.sqlStorage;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.KeyValueInfo;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.session.SessionInfo;
import io.supertokens.pluginInterface.session.SessionStorage;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface SessionSQLStorage extends SessionStorage, SQLStorage {

    void removeLegacyAccessTokenSigningKey_Transaction(AppIdentifier appIdentifier, TransactionConnection con)
            throws StorageQueryException;

    KeyValueInfo getLegacyAccessTokenSigningKey_Transaction(AppIdentifier appIdentifier,
                                                            TransactionConnection con) throws StorageQueryException;

    KeyValueInfo[] getAccessTokenSigningKeys_Transaction(AppIdentifier appIdentifier, TransactionConnection con)
            throws StorageQueryException;

    void addAccessTokenSigningKey_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                              KeyValueInfo info)
            throws StorageQueryException, TenantOrAppNotFoundException;

    KeyValueInfo getRefreshTokenSigningKey_Transaction(AppIdentifier appIdentifier, TransactionConnection con)
            throws StorageQueryException;

    void setRefreshTokenSigningKey_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                               KeyValueInfo info)
            throws StorageQueryException, TenantOrAppNotFoundException;

    SessionInfo getSessionInfo_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                           String sessionHandle)
            throws StorageQueryException;

    void updateSessionInfo_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                       String sessionHandle, String refreshTokenHash2,
                                       long expiry, boolean useStaticKey) throws StorageQueryException;

    void deleteSessionsOfUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    // Inserts a session inside the caller's transaction so the insert can be serialized with account
    // link/unlink under the same user lock. Used by session creation to close the race where a session
    // created concurrently with unlinkAccounts survived the unlink's revocation (GHSA-2pc4-vw9j-454c).
    // Default implementation falls back to the non-transactional insert (no participation in the caller's
    // lock, so no extra serialization); storages that implement account linking override this to insert
    // within the transaction. The default keeps other storages compiling and working unchanged.
    default void createNewSession_Transaction(TransactionConnection con, TenantIdentifier tenantIdentifier,
                                              String sessionHandle, String userId, String refreshTokenHash2,
                                              JsonObject userDataInDatabase, long expiry, JsonObject userDataInJWT,
                                              long createdAtTime, boolean useStaticKey)
            throws StorageQueryException, TenantOrAppNotFoundException {
        createNewSession(tenantIdentifier, sessionHandle, userId, refreshTokenHash2, userDataInDatabase, expiry,
                userDataInJWT, createdAtTime, useStaticKey);
    }
}
