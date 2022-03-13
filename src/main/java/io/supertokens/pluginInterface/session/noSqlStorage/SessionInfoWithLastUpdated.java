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

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.session.SessionInfo;

public class SessionInfoWithLastUpdated extends SessionInfo {
    public String lastUpdatedSign;

    public SessionInfoWithLastUpdated(String sessionHandle, String userId, String refreshTokenHash2,
            JsonObject userDataInDatabase, long expiry, JsonObject userDataInJWT, JsonObject grantPayload,
            long timeCreated, String lastUpdatedSign) {
        super(sessionHandle, userId, refreshTokenHash2, userDataInDatabase, expiry, userDataInJWT, grantPayload,
                timeCreated);
        this.lastUpdatedSign = lastUpdatedSign;
    }
}
