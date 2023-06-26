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

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.oauth2.exception.*;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import java.util.List;

public interface OAuth2Storage extends NonAuthRecipeStorage {
    OAuth2Client getOAuth2ClientById(AppIdentifier appIdentifier, String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException;
    void removeOAuth2Client(AppIdentifier appIdentifier, String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException;

    List<OAuth2ClientScope> getOAuth2ClientScopes(AppIdentifier appIdentifier, String clientId)
            throws StorageQueryException, UnknownOAuth2ClientIdException;
    void removeOAuth2ClientScope(AppIdentifier appIdentifier, String clientId, String scope)
            throws StorageQueryException, UnknownOAuth2ClientIdException, UnknownOAuth2ScopeException;

    void createOAuth2Scope(AppIdentifier appIdentifier, String scope)
            throws StorageQueryException, DuplicateOAuth2ScopeException, TenantOrAppNotFoundException;
    void updateOAuth2Scope(AppIdentifier appIdentifier, String scope, String newName)
            throws StorageQueryException, DuplicateOAuth2ScopeException, TenantOrAppNotFoundException;
    void removeOAuth2Scope(AppIdentifier appIdentifier, String scope)
            throws StorageQueryException, DuplicateOAuth2ScopeException, TenantOrAppNotFoundException;
    List<String> getOAuth2Scopes(AppIdentifier appIdentifier)
            throws StorageQueryException, TenantOrAppNotFoundException;

    void createOAuth2AuthorizationCode(TenantIdentifier tenantIdentifier, String codeHash, String sessionHandle, String clientId, long createdAtMs,
                                       long expiresAtMs, List<String> scope, String redirectUri, String accessType,
                                       String codeChallenge, String codeChallengeMethod, String queryString)
            throws StorageQueryException, DuplicateOAuth2AuthorizationCodeHash;

    void updateOAuth2TokenInfoByRefreshTokenHash(TenantIdentifier tenantIdentifier, OAuth2TokenInfo tokenInfo)
            throws StorageQueryException, UnknownOAuth2RefreshTokenHashException, DuplicateOAuth2RefreshTokenHash, DuplicateOAuth2AccessTokenHash;

    OAuth2TokenInfo getOAuth2TokenInBySessionHandle(TenantIdentifier tenantIdentifier,String clientId, String sessionHandle)
            throws StorageQueryException;

    void removeOAuth2TokensExpiredBefore(long now) throws StorageQueryException;
    void removeOAuth2TokensBySessionHandle(TenantIdentifier tenantIdentifier, String clientId, String sessionHandle) throws StorageQueryException;
    void removeOAuth2TokensByClientId(TenantIdentifier tenantIdentifier, String clientId) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesExpiredBefore(long now) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesByClientId(TenantIdentifier tenantIdentifier, String clientId) throws StorageQueryException;
    void removeOAuth2AuthorizationCodesBySessionHandle(TenantIdentifier tenantIdentifier, String sessionHandle) throws StorageQueryException;
}