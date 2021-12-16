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
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;

import javax.annotation.Nullable;

public interface SessionStorage extends Storage {
    void createNewSession(String sessionHandle, String userId, String refreshTokenHash2, JsonObject userDataInDatabase,
            long expiry, JsonObject userDataInJWT, long createdAtTime) throws StorageQueryException;

    void deleteSessionsOfUser(String userId) throws StorageQueryException;

    // return number of rows else throw UnsupportedOperationException
    int getNumberOfSessions() throws StorageQueryException;

    int deleteSession(String[] sessionHandles) throws StorageQueryException;

    String[] getAllSessionHandlesForUser(String userId) throws StorageQueryException;

    void deleteAllExpiredSessions() throws StorageQueryException;

    SessionInfo getSession(String sessionHandle) throws StorageQueryException;

    int updateSession(String sessionHandle, @Nullable JsonObject sessionData, @Nullable JsonObject jwtPayload)
            throws StorageQueryException;

    void removeAccessTokenSigningKeysBefore(long time) throws StorageQueryException;
}
