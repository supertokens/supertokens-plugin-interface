/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.webauthn.slqStorage;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.webauthn.AccountRecoveryTokenInfo;
import io.supertokens.pluginInterface.webauthn.WebAuthNOptions;
import io.supertokens.pluginInterface.webauthn.WebAuthNStorage;
import io.supertokens.pluginInterface.webauthn.WebAuthNStoredCredential;

public interface WebAuthNSQLStorage extends WebAuthNStorage, SQLStorage {

    WebAuthNStoredCredential saveCredentials_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                         WebAuthNStoredCredential credential) throws StorageQueryException;

    WebAuthNOptions loadOptionsById_Transaction(TenantIdentifier tenantIdentifier,  TransactionConnection con, String optionsId) throws  StorageQueryException;

    AuthRecipeUserInfo signUp_Transaction(TenantIdentifier tenantIdentifier,  TransactionConnection con, String userId, String email, String relyingPartyId)
            throws StorageQueryException, DuplicateUserIdException, TenantOrAppNotFoundException,
            DuplicateEmailException;

    AccountRecoveryTokenInfo getAccountRecoveryTokenInfoByToken_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String token) throws StorageQueryException;
    void deleteAccountRecoveryTokenByEmail_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String email) throws StorageQueryException;
}
