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

package io.supertokens.pluginInterface.session;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

import javax.annotation.Nullable;

public interface SessionStorage extends NonAuthRecipeStorage {
    void createNewSession(TenantIdentifier tenantIdentifier, String sessionHandle, String userId,
                          String refreshTokenHash2, JsonObject userDataInDatabase,
                          long expiry, JsonObject userDataInJWT, long createdAtTime) throws StorageQueryException;

    void deleteSessionsOfUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    // return number of rows else throw UnsupportedOperationException
    int getNumberOfSessions(TenantIdentifier tenantIdentifier) throws StorageQueryException;

    int deleteSession(TenantIdentifier tenantIdentifier, String[] sessionHandles) throws StorageQueryException;

    String[] getAllNonExpiredSessionHandlesForUser(TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

    // we purposely do not add TenantIdentifier to this query cause
    // this is called from a cronjob that runs per user pool ID
    void deleteAllExpiredSessions() throws StorageQueryException;

    SessionInfo getSession(TenantIdentifier tenantIdentifier, String sessionHandle) throws StorageQueryException;

    int updateSession(TenantIdentifier tenantIdentifier, String sessionHandle, @Nullable JsonObject sessionData,
                      @Nullable JsonObject jwtPayload)
            throws StorageQueryException;

    void removeAccessTokenSigningKeysBefore(TenantIdentifier tenantIdentifier, long time) throws StorageQueryException;
}
