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

package io.supertokens.pluginInterface.sqlStorage;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.KeyValueInfo;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;

public abstract class SQLStorage implements Storage {
    public abstract <T> T startTransaction(TransactionLogic<T> logic)
            throws StorageQueryException, StorageTransactionLogicException;

    public abstract void commitTransaction(TransactionConnection con) throws StorageQueryException;

    public abstract KeyValueInfo getAccessTokenSigningKey_Transaction(TransactionConnection con)
            throws StorageQueryException;

    public abstract void setAccessTokenSigningKey_Transaction(TransactionConnection con, KeyValueInfo info)
            throws StorageQueryException;

    public abstract KeyValueInfo getRefreshTokenSigningKey_Transaction(TransactionConnection con)
            throws StorageQueryException;

    public abstract void setRefreshTokenSigningKey_Transaction(TransactionConnection con, KeyValueInfo info)
            throws StorageQueryException;

    public abstract SessionInfo getSessionInfo_Transaction(TransactionConnection con, String sessionHandle)
            throws StorageQueryException;

    public abstract void updateSessionInfo_Transaction(TransactionConnection con, String sessionHandle,
                                                       String refreshTokenHash2, long expiry)
            throws StorageQueryException;

    public abstract void setKeyValue_Transaction(TransactionConnection con, String key, KeyValueInfo info)
            throws StorageQueryException;

    public abstract KeyValueInfo getKeyValue_Transaction(TransactionConnection con, String key)
            throws StorageQueryException;

    public interface TransactionLogic<T> {
        T mainLogicAndCommit(TransactionConnection con)
                throws StorageQueryException, StorageTransactionLogicException;
    }

    public static class SessionInfo {
        public String sessionHandle;
        public String userId;
        public String refreshTokenHash2;
        public JsonObject userDataInDatabase;
        public long expiry;
        public JsonObject userDataInJWT;
        public long timeCreated;

        public SessionInfo(String sessionHandle, String userId, String refreshTokenHash2, JsonObject userDataInDatabase,
                           long expiry, JsonObject userDataInJWT, long timeCreated) {
            this.sessionHandle = sessionHandle;
            this.userId = userId;
            this.refreshTokenHash2 = refreshTokenHash2;
            this.userDataInDatabase = userDataInDatabase;
            this.expiry = expiry;
            this.userDataInJWT = userDataInJWT;
            this.timeCreated = timeCreated;
        }
    }
}
