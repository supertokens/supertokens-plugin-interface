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

package io.supertokens.pluginInterface.passwordless;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.passwordless.exception.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PasswordlessStorage extends AuthRecipeStorage {
    void createDeviceWithCode(TenantIdentifier tenantIdentifier, @Nullable String email, @Nullable String phoneNumber,
                              @Nonnull String linkCodeSalt,
                              PasswordlessCode code) throws StorageQueryException, DuplicateDeviceIdHashException,
            DuplicateCodeIdException, DuplicateLinkCodeHashException, TenantOrAppNotFoundException;

    void createCode(TenantIdentifier tenantIdentifier, PasswordlessCode code)
            throws StorageQueryException, UnknownDeviceIdHash, DuplicateCodeIdException, DuplicateLinkCodeHashException;

    void createUser(TenantIdentifier tenantIdentifier, CreateUserInfo user)
            throws StorageQueryException, DuplicateEmailException, DuplicatePhoneNumberException,
            DuplicateUserIdException, TenantOrAppNotFoundException;

    void deletePasswordlessUser(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    PasswordlessDevice getDevice(TenantIdentifier tenantIdentifier, String deviceIdHash) throws StorageQueryException;

    PasswordlessDevice[] getDevicesByEmail(TenantIdentifier tenantIdentifier, @Nonnull String email)
            throws StorageQueryException;

    PasswordlessDevice[] getDevicesByPhoneNumber(TenantIdentifier tenantIdentifier, @Nonnull String phoneNumber)
            throws StorageQueryException;

    PasswordlessCode[] getCodesOfDevice(TenantIdentifier tenantIdentifier, String deviceIdHash)
            throws StorageQueryException;

    PasswordlessCode[] getCodesBefore(TenantIdentifier tenantIdentifier, long time) throws StorageQueryException;

    PasswordlessCode getCode(TenantIdentifier tenantIdentifier, String codeId) throws StorageQueryException;

    PasswordlessCode getCodeByLinkCodeHash(TenantIdentifier tenantIdentifier, String linkCode)
            throws StorageQueryException;

    UserInfo getUserById(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    UserInfo getUserByEmail(TenantIdentifier tenantIdentifier, @Nonnull String email) throws StorageQueryException;

    UserInfo getUserByPhoneNumber(TenantIdentifier tenantIdentifier, @Nonnull String phoneNumber)
            throws StorageQueryException;
}