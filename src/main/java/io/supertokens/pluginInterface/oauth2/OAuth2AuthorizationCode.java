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

import javax.annotation.Nullable;
import java.util.List;

public class OAuth2AuthorizationCode {
    public final String codeHash;
    public final String sessionHandle;
    public final String clientId;
    public final long createdAtMs;
    public final long expiresAtMs;
    public final List<String> scope;
    public final String redirectUri;
    public final String accessType;
    public final String codeChallenge;
    public final String codeChallengeMethod;

    public OAuth2AuthorizationCode(String codeHash, String sessionHandle, String clientId, long createdAtMs,
                                   long expiresAtMs, List<String> scope, String redirectUri, String accessType,
                                   String codeChallenge, String codeChallengeMethod) {
        this.codeHash = codeHash;
        this.sessionHandle = sessionHandle;
        this.clientId = clientId;
        this.createdAtMs = createdAtMs;
        this.expiresAtMs = expiresAtMs;
        this.scope = scope;
        this.redirectUri = redirectUri;
        this.accessType = accessType;
        this.codeChallenge = codeChallenge;
        this.codeChallengeMethod = codeChallengeMethod;
    }
}
