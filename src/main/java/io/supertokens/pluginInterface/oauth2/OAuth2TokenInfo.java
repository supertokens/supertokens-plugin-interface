/*
 *    Copyright (c) 2023, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.oauth2;

public class OAuth2TokenInfo {
    public final String clientId;
    public final String userId;
    public final String sessionHandle;
    public final String scope;
    public final String accessTokenHash;
    public final String refreshTokenHash;
    public final long createdAtMs;
    public final long accessTokenExpiresAtMs;
    public final Long refreshTokenHashExpiresAtMs;

    public OAuth2TokenInfo(String clientId, String userId, String sessionHandle, String scope, String accessTokenHash,
                           String refreshTokenHash, long createdAtMs, long accessTokenExpiresAtMs,
                           Long refreshTokenHashExpiresAtMs) {
        this.clientId = clientId;
        this.userId = userId;
        this.sessionHandle = sessionHandle;
        this.scope = scope;
        this.accessTokenHash = accessTokenHash;
        this.refreshTokenHash = refreshTokenHash;
        this.createdAtMs = createdAtMs;
        this.accessTokenExpiresAtMs = accessTokenExpiresAtMs;
        this.refreshTokenHashExpiresAtMs = refreshTokenHashExpiresAtMs;
    }
}
