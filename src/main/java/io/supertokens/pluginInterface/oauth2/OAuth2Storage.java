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
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.oauth2.exception.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface OAuth2Storage extends NonAuthRecipeStorage {
    void updateOAuth2Client(AppIdentifier appIdentifier, @Nonnull String clientId, @Nonnull String name,
                            @Nonnull String clientSecretHash, @Nonnull List<String> redirectUris, long updatedAtMs)
            throws StorageQueryException, UnknownOAuth2ClientIdException, DuplicateOAuth2ClientSecretHash;
    OAuth2Client getOAuth2ClientById(AppIdentifier appIdentifier, @Nonnull String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException;
    void removeOAuth2Client(AppIdentifier appIdentifier, @Nonnull String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException;

    List<OAuth2ClientScope> getOAuth2ClientScopes(AppIdentifier appIdentifier, @Nonnull String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException, UnknownOAuth2ScopeException;
    void removeOAuth2ClientScope(AppIdentifier appIdentifier, @Nonnull String clientId, @Nonnull String scope)
            throws StorageQueryException, UnknownOAuth2ClientIdException, UnknownOAuth2ScopeException;

    void createOAuth2Scope(AppIdentifier appIdentifier, @Nonnull String scope)
            throws StorageQueryException, DuplicateOAuth2ScopeException;
    void updateOAuth2Scope(AppIdentifier appIdentifier, @Nonnull String scope, String newName)
            throws StorageQueryException, DuplicateOAuth2ScopeException;
    void removeOAuth2Scope(AppIdentifier appIdentifier, @Nonnull String scope)
            throws StorageQueryException, DuplicateOAuth2ScopeException;
    List<String> getOAuth2Scopes(AppIdentifier appIdentifier)
            throws StorageQueryException;

    void createOAuth2AuthorizationCode(TenantIdentifier tenantIdentifier, String codeHash, String sessionHandle, String clientId, long createdAtMs,
                                       long expiresAtMs, String scope, String redirectUri, String accessType,
                                       String codeChallenge, String codeChallengeMethod)
            throws StorageQueryException, DuplicateOAuth2AuthorizationCodeHash;

    OAuth2TokenInfo getOAuth2TokenInfoByAccessTokenHash(TenantIdentifier tenantIdentifier, String accessTokenHash)
            throws StorageQueryException, UnknownOAuth2AccessTokenHashHashException;
    void updateOAuth2TokenInfoByRefreshTokenHash(TenantIdentifier tenantIdentifier, String refreshTokenHash,
                                                 String newAccessTokenHash, String newRefreshTokenHash,
                                                 long accessTokenExpiresAtMs, Long refreshTokenHashExpiresAtMs)
            throws StorageQueryException, UnknownOAuth2RefreshTokenHashHashException, DuplicateOAuth2RefreshTokenHash, DuplicateOAuth2AccessTokenHash;

    void removeOAuth2TokensExpiredBefore(long now) throws StorageQueryException;
    void removeOAuth2TokensBySessionHandle(TenantIdentifier tenantIdentifier, String sessionHandle) throws StorageQueryException;
    void removeOAuth2TokenByAccessTokenHash(TenantIdentifier tenantIdentifier, String accessTokenHash) throws StorageQueryException;
    void removeOAuth2TokenByClientId(TenantIdentifier tenantIdentifier, String accessTokenHash) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesExpiredBefore(long now) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesByClientId(long now) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesBySessionHandle(long now) throws StorageQueryException;
}