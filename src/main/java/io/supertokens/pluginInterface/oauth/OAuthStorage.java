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
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.oauth.exception.DuplicateOAuthLogoutChallengeException;

import java.util.List;

public interface OAuthStorage extends NonAuthRecipeStorage {

    public boolean doesOAuthClientIdExist(AppIdentifier appIdentifier, String clientId) throws
            StorageQueryException;

    public void addOrUpdateOauthClient(AppIdentifier appIdentifier, String clientId, boolean isClientCredentialsOnly) throws StorageQueryException;

    public boolean deleteOAuthClient(AppIdentifier appIdentifier, String clientId) throws StorageQueryException;

    public List<String> listOAuthClients(AppIdentifier appIdentifier) throws StorageQueryException;

    public void revokeOAuthTokensBasedOnTargetFields(AppIdentifier appIdentifier, OAuthRevokeTargetType targetType, String targetValue, long exp) throws StorageQueryException;

    public boolean isOAuthTokenRevokedBasedOnTargetFields(AppIdentifier appIdentifier, OAuthRevokeTargetType[] targetTypes, String[] targetValues, long issuedAt) throws StorageQueryException;

    public void addOAuthM2MTokenForStats(AppIdentifier appIdentifier, String clientId, long iat, long exp) throws StorageQueryException;

    public void cleanUpExpiredAndRevokedOAuthTokensList() throws StorageQueryException;

    public void addOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge, String clientId, String postLogoutRedirectionUri, String sessionHandle, String state, long timeCreated) throws
            DuplicateOAuthLogoutChallengeException, StorageQueryException;

    public OAuthLogoutChallenge getOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge) throws StorageQueryException;

    public void deleteOAuthLogoutChallenge(AppIdentifier appIdentifier, String challenge) throws StorageQueryException;

    public void deleteOAuthLogoutChallengesBefore(long time) throws StorageQueryException;

    public int countTotalNumberOfOAuthClients(AppIdentifier appIdentifier) throws StorageQueryException;

    public int countTotalNumberOfClientCredentialsOnlyOAuthClients(AppIdentifier appIdentifier) throws StorageQueryException;

    public int countTotalNumberOfOAuthM2MTokensCreatedSince(AppIdentifier appIdentifier, long since) throws StorageQueryException;

    public int countTotalNumberOfOAuthM2MTokensAlive(AppIdentifier appIdentifier) throws StorageQueryException;
}
