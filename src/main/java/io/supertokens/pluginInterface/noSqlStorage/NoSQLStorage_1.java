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
 *
 */

package io.supertokens.pluginInterface.noSqlStorage;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.KeyValueInfoWithLastUpdated;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public abstract class NoSQLStorage_1 implements Storage {
    public abstract boolean setKeyValue_Transaction(String key, KeyValueInfoWithLastUpdated info)
            throws StorageQueryException;

    public abstract KeyValueInfoWithLastUpdated getKeyValue_Transaction(String key) throws StorageQueryException;

    public abstract KeyValueInfoWithLastUpdated getAccessTokenSigningKey_Transaction() throws StorageQueryException;

    public abstract boolean setAccessTokenSigningKey_Transaction(KeyValueInfoWithLastUpdated info)
            throws StorageQueryException;

    public abstract KeyValueInfoWithLastUpdated getRefreshTokenSigningKey_Transaction() throws
            StorageQueryException;

    public abstract boolean setRefreshTokenSigningKey_Transaction(KeyValueInfoWithLastUpdated info)
            throws StorageQueryException;

    public abstract SessionInfoWithLastUpdated getSessionInfo_Transaction(String sessionHandle)
            throws StorageQueryException;

    public abstract boolean updateSessionInfo_Transaction(String sessionHandle,
                                                          String refreshTokenHash2, long expiry, String lastUpdatedSign)
            throws StorageQueryException;

    public static class SessionInfoWithLastUpdated {
        public String sessionHandle;
        public String userId;
        public String refreshTokenHash2;
        public JsonObject userDataInDatabase;
        public long expiry;
        public JsonObject userDataInJWT;
        public long timeCreated;
        public String lastUpdatedSign;

        public SessionInfoWithLastUpdated(String sessionHandle, String userId, String refreshTokenHash2,
                                          JsonObject userDataInDatabase,
                                          long expiry, JsonObject userDataInJWT, long timeCreated,
                                          String lastUpdatedSign) {
            this.sessionHandle = sessionHandle;
            this.userId = userId;
            this.refreshTokenHash2 = refreshTokenHash2;
            this.userDataInDatabase = userDataInDatabase;
            this.expiry = expiry;
            this.userDataInJWT = userDataInJWT;
            this.timeCreated = timeCreated;
            this.lastUpdatedSign = lastUpdatedSign;
        }
    }
}
