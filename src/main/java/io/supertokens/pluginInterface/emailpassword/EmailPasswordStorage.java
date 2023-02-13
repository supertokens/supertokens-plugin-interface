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
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

public interface EmailPasswordStorage extends AuthRecipeStorage {

    void signUp(TenantIdentifier tenantIdentifier, UserInfo userInfo)
            throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    void deleteEmailPasswordUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    UserInfo getUserInfoUsingId(TenantIdentifier tenantIdentifier, String id) throws StorageQueryException;

    UserInfo getUserInfoUsingEmail(TenantIdentifier tenantIdentifier, String email) throws StorageQueryException;

    void addPasswordResetToken(TenantIdentifier tenantIdentifier, PasswordResetTokenInfo passwordResetTokenInfo)
            throws StorageQueryException, UnknownUserIdException, DuplicatePasswordResetTokenException;

    PasswordResetTokenInfo getPasswordResetTokenInfo(TenantIdentifier tenantIdentifier, String token)
            throws StorageQueryException;

    // we purposely do not add TenantIdentifier to this query cause
    // this is called from a cronjob that runs per user pool ID
    void deleteExpiredPasswordResetTokens() throws StorageQueryException;

    PasswordResetTokenInfo[] getAllPasswordResetTokenInfoForUser(TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

}