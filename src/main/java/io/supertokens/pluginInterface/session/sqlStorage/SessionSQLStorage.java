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

import io.supertokens.pluginInterface.KeyValueInfo;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.session.SessionInfo;
import io.supertokens.pluginInterface.session.SessionStorage;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.SessionObject;

public interface SessionSQLStorage extends SessionStorage, SQLStorage {

    void removeLegacyAccessTokenSigningKey_Transaction(SessionObject sessionInstance) throws StorageQueryException;

    KeyValueInfo getLegacyAccessTokenSigningKey_Transaction(SessionObject sessionInstance) throws StorageQueryException;

    KeyValueInfo[] getAccessTokenSigningKeys_Transaction(SessionObject sessionInstance) throws StorageQueryException;

    void addAccessTokenSigningKey_Transaction(SessionObject sessionInstance, KeyValueInfo info)
            throws StorageQueryException;

    KeyValueInfo getRefreshTokenSigningKey_Transaction(SessionObject sessionInstance) throws StorageQueryException;

    void setRefreshTokenSigningKey_Transaction(SessionObject sessionInstance, KeyValueInfo info)
            throws StorageQueryException;

    SessionInfo getSessionInfo_Transaction(SessionObject sessionInstance, String sessionHandle)
            throws StorageQueryException;

    void updateSessionInfo_Transaction(SessionObject sessionInstance, String sessionHandle, String refreshTokenHash2,
            long expiry) throws StorageQueryException;
}
