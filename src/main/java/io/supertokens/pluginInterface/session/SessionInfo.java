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

public class SessionInfo {
    transient public String refreshTokenHash2;
    public String sessionHandle;
    public String userId;
    public String recipeUserId;
    public JsonObject userDataInDatabase;
    public long expiry;
    public JsonObject userDataInJWT;
    public long timeCreated;
    public transient boolean useStaticKey;

    public SessionInfo(String sessionHandle, String userId, String recipeUserId, String refreshTokenHash2,
                       JsonObject userDataInDatabase,
                       long expiry, JsonObject userDataInJWT, long timeCreated, boolean useStaticKey) {
        this.sessionHandle = sessionHandle;
        this.userId = userId == null ? recipeUserId : userId;
        this.recipeUserId = recipeUserId;
        this.refreshTokenHash2 = refreshTokenHash2;
        this.userDataInDatabase = userDataInDatabase;
        this.expiry = expiry;
        this.userDataInJWT = userDataInJWT;
        this.timeCreated = timeCreated;
        this.useStaticKey = useStaticKey;
    }
}
