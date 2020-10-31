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

package io.supertokens.pluginInterface.session.noSqlStorage;

import io.supertokens.pluginInterface.KeyValueInfoWithLastUpdated;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.noSqlStorage.NoSQLStorage_1;
import io.supertokens.pluginInterface.session.SessionStorage;

public interface SessionNoSQLStorage_1 extends SessionStorage, NoSQLStorage_1 {
    KeyValueInfoWithLastUpdated getAccessTokenSigningKey_Transaction() throws StorageQueryException;

    boolean setAccessTokenSigningKey_Transaction(KeyValueInfoWithLastUpdated info)
            throws StorageQueryException;

    KeyValueInfoWithLastUpdated getRefreshTokenSigningKey_Transaction() throws
            StorageQueryException;

    boolean setRefreshTokenSigningKey_Transaction(KeyValueInfoWithLastUpdated info)
            throws StorageQueryException;

    SessionInfoWithLastUpdated getSessionInfo_Transaction(String sessionHandle)
            throws StorageQueryException;

    boolean updateSessionInfo_Transaction(String sessionHandle,
                                          String refreshTokenHash2, long expiry, String lastUpdatedSign)
            throws StorageQueryException;
}
