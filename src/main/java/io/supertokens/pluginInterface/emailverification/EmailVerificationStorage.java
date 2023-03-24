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

package io.supertokens.pluginInterface.emailverification;

import io.supertokens.pluginInterface.emailverification.exception.DuplicateEmailVerificationTokenException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

public interface EmailVerificationStorage extends NonAuthRecipeStorage {

    void addEmailVerificationToken(AppIdentifier appIdentifier, EmailVerificationTokenInfo emailVerificationInfo)
            throws StorageQueryException, DuplicateEmailVerificationTokenException, TenantOrAppNotFoundException;

    EmailVerificationTokenInfo getEmailVerificationTokenInfo(AppIdentifier appIdentifier, String token)
            throws StorageQueryException;

    void deleteEmailVerificationUserInfo(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    void revokeAllTokens(AppIdentifier appIdentifier, String userId, String email) throws StorageQueryException;

    void unverifyEmail(AppIdentifier appIdentifier, String userId, String email) throws StorageQueryException;

    void deleteExpiredEmailVerificationTokens() throws StorageQueryException;

    EmailVerificationTokenInfo[] getAllEmailVerificationTokenInfoForUser(AppIdentifier appIdentifier, String userId,
                                                                         String email)
            throws StorageQueryException;

    boolean isEmailVerified(AppIdentifier appIdentifier, String userId, String email) throws StorageQueryException;

}