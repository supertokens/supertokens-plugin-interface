/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.oauth;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.oauth.exception.DuplicateOAuthLogoutChallengeException;
import io.supertokens.pluginInterface.oauth.exception.OAuthClientNotFoundException;

import java.util.List;

public interface OAuthStorage extends NonAuthRecipeStorage {

    public OAuthClient getOAuthClientById(AppIdentifier appIdentifier, String clientId) throws
            OAuthClientNotFoundException, StorageQueryException;

    public void addOrUpdateOauthClient(AppIdentifier appIdentifier, String clientId, String clientSecret, boolean isClientCredentialsOnly, boolean enableRefreshTokenRotation) throws TenantOrAppNotFoundException, StorageQueryException;

    public boolean deleteOAuthClient(AppIdentifier appIdentifier, String clientId) throws StorageQueryException;

    public List<OAuthClient> getOAuthClients(AppIdentifier appIdentifier, List<String> clientIds) throws StorageQueryException;

    public void addOAuthM2MTokenForStats(AppIdentifier appIdentifier, String clientId, long iat, long exp) throws OAuthClientNotFoundException, StorageQueryException;

    public void deleteExpiredOAuthM2MTokens(long exp) throws StorageQueryException;

    public void addOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge, String clientId, String postLogoutRedirectionUri, String sessionHandle, String state, long timeCreated) throws
            DuplicateOAuthLogoutChallengeException, OAuthClientNotFoundException, StorageQueryException;

    public OAuthLogoutChallenge getOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge) throws StorageQueryException;

    public void deleteOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge) throws StorageQueryException;

    public void deleteOAuthLogoutChallengesBefore(long time) throws StorageQueryException;

    public void createOrUpdateOAuthSession(AppIdentifier appIdentifier, String gid, String clientId, String externalRefreshToken, String internalRefreshToken, String sessionHandle, List<String> jtis, long exp) throws StorageQueryException;

    public String getRefreshTokenMapping(AppIdentifier appIdentifier, String externalRefreshToken) throws StorageQueryException;

    public void deleteRefreshTokenMapping(AppIdentifier appIdentifier, String externalRefreshToken) throws StorageQueryException;

    public void deleteExpiredOAuthSessions(long exp) throws StorageQueryException;

    public int countTotalNumberOfOAuthClients(AppIdentifier appIdentifier) throws StorageQueryException;

    public int countTotalNumberOfClientCredentialsOnlyOAuthClients(AppIdentifier appIdentifier) throws StorageQueryException;

    public int countTotalNumberOfOAuthM2MTokensCreatedSince(AppIdentifier appIdentifier, long since) throws StorageQueryException;

    public int countTotalNumberOfOAuthM2MTokensAlive(AppIdentifier appIdentifier) throws StorageQueryException;

    public boolean revokeOAuthTokenByGID( AppIdentifier appIdentifier, String gid) throws StorageQueryException;

    public boolean revokeOAuthTokenByClientId(AppIdentifier appIdentifier, String clientId) throws StorageQueryException;

    public boolean revokeOAuthTokenBySessionHandle(AppIdentifier appIdentifier, String sessionHandle) throws StorageQueryException;

    public boolean revokeOAuthTokenByJTI(AppIdentifier appIdentifier, String gid, String jti) throws StorageQueryException;

    public boolean isOAuthTokenRevokedByGID(AppIdentifier appIdentifier, String gid) throws StorageQueryException;

    public boolean isOAuthTokenRevokedByClientId(AppIdentifier appIdentifier, String clientId) throws StorageQueryException;

    public boolean isOAuthTokenRevokedBySessionHandle(AppIdentifier appIdentifier, String sessionHandle) throws StorageQueryException;

    public boolean isOAuthTokenRevokedByJTI(AppIdentifier appIdentifier, String gid, String jti) throws StorageQueryException;
}

