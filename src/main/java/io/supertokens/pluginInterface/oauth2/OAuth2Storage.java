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

import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.oauth2.exception.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface OAuth2Storage {
    void createOAuth2Client(@Nonnull String client_id, @Nonnull String name, @Nonnull String client_secret_hash, @Nonnull List<String> redirect_uris, boolean enabled, long created_at) throws StorageQueryException, DuplicateOAuth2ClientIdException;
    void updateOAuth2Client(@Nonnull String client_id, @Nonnull String name, @Nonnull String client_secret_hash, @Nonnull List<String> redirect_uris, boolean enabled, long updated_at) throws StorageQueryException, UnknownOAuth2ClientIdException;
    OAuth2Client getOAuth2ClientById(@Nonnull String client_id) throws StorageQueryException, UnknownOAuth2ClientIdException;

    void addOrSetOAuth2ClientScope(@Nonnull String client_id, @Nonnull String scope, boolean requiresConsent) throws StorageQueryException, UnknownOAuth2ClientIdException, UnknownOAuth2ScopeException;
    void removeOAuth2ClientScope(@Nonnull String client_id, @Nonnull String scope) throws StorageQueryException, UnknownOAuth2ClientIdException, UnknownOAuth2ScopeException;

    void createOAuth2Scope(@Nonnull String scope) throws StorageQueryException, DuplicateOAuth2ScopeException;
    void removeOAuth2Scope(@Nonnull String scope) throws StorageQueryException, DuplicateOAuth2ScopeException;
    List<String> getOAuth2Scopes() throws StorageQueryException;

    void createOAuth2AuthorizationCode(String codeHash, String sessionHandle, String clientId, long createdAtMs,
                                       long expiresAtMs, String scope, String redirectUri, String accessType,
                                       String codeChallenge, String codeChallengeMethod) throws StorageQueryException, DuplicateOAuth2AuthorizationCodeHash;
    void updateOAuth2TokenInfoByRefreshTokenHash(String refreshTokenHash, String accessTokenHash,
                                                 String newRefreshTokenHash, long accessTokenExpiresAtMs,
                                                 Long refreshTokenHashExpiresAtMs) throws StorageQueryException, UnknownOAuth2RefreshTokenHashHashException, DuplicateOAuth2RefreshTokenHash, DuplicateOAuth2AccessTokenHash;
}