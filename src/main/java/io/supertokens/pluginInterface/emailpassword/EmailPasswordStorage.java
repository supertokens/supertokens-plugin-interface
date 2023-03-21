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

package io.supertokens.pluginInterface.emailpassword;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicatePasswordResetTokenException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.emailpassword.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

public interface EmailPasswordStorage extends AuthRecipeStorage {

    // we pass tenantIdentifier here cause this also adds to the userId <-> tenantId mapping
    void signUp(TenantIdentifier tenantIdentifier, UserInfo userInfo)
            throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    // this deletion of a user is app wide since the same user ID can be shared across tenants
    UserInfo getUserInfoUsingId(AppIdentifier appIdentifier, String id) throws StorageQueryException;

    // Here we pass in TenantIdentifier cause the same email can be shared across tenants, but yield different
    // user IDs
    UserInfo getUserInfoUsingEmail(TenantIdentifier tenantIdentifier, String email) throws StorageQueryException;

    // password reset stuff is app wide cause changing the password for a user affects all the tenants
    // across which it's shared.
    void addPasswordResetToken(AppIdentifier appIdentifier, PasswordResetTokenInfo passwordResetTokenInfo)
            throws StorageQueryException, UnknownUserIdException, DuplicatePasswordResetTokenException;

    PasswordResetTokenInfo getPasswordResetTokenInfo(AppIdentifier appIdentifier, String token)
            throws StorageQueryException;

    // we purposely do not add TenantIdentifier to this query cause
    // this is called from a cronjob that runs per user pool ID
    void deleteExpiredPasswordResetTokens() throws StorageQueryException;

    PasswordResetTokenInfo[] getAllPasswordResetTokenInfoForUser(AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

}