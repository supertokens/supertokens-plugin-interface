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

package io.supertokens.pluginInterface.webauthn;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

import java.util.List;

public interface WebAuthNStorage extends AuthRecipeStorage {

    WebAuthNStoredCredential saveCredentials(TenantIdentifier tenantIdentifier, WebAuthNStoredCredential credential) throws StorageQueryException;

    WebAuthNOptions saveGeneratedOptions(TenantIdentifier tenantIdentifier, WebAuthNOptions optionsToSave) throws StorageQueryException;

    WebAuthNOptions loadOptionsById(TenantIdentifier tenantIdentifier, String optionsId) throws  StorageQueryException;

    WebAuthNStoredCredential loadCredentialById(TenantIdentifier tenantIdentifier, String credentialId) throws  StorageQueryException;

    void addRecoverAccountToken(TenantIdentifier tenantIdentifier, AccountRecoveryTokenInfo accountRecoveryTokenInfo) throws DuplicateRecoverAccountTokenException, StorageQueryException;

    void removeCredential(TenantIdentifier tenantIdentifier, String userId, String credentialId)
            throws StorageQueryException, CredentialNotExistsException;

    void removeOptions(TenantIdentifier tenantIdentifier, String optionsId)
            throws StorageQueryException, OptionsNotExistsException;

    List<WebAuthNStoredCredential> listCredentialsForUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    void updateUserEmail(TenantIdentifier tenantIdentifier, String userId, String newEmail)
            throws StorageQueryException, UserIdNotFoundException, DuplicateUserEmailException;

    void deleteExpiredAccountRecoveryTokens() throws StorageQueryException;

    void deleteExpiredGeneratedOptions() throws StorageQueryException;
}
