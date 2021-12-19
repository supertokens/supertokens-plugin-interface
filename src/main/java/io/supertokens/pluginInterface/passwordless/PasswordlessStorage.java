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
import io.supertokens.pluginInterface.passwordless.exception.DuplicateCodeIdException;
import io.supertokens.pluginInterface.passwordless.exception.DuplicateDeviceIdHashException;
import io.supertokens.pluginInterface.passwordless.exception.DuplicateLinkCodeHashException;
import io.supertokens.pluginInterface.passwordless.exception.DuplicatePhoneNumberException;
import io.supertokens.pluginInterface.passwordless.exception.UnknownDeviceIdHash;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PasswordlessStorage extends AuthRecipeStorage {
    void createDeviceWithCode(@Nullable String email, @Nullable String phoneNumber, @Nonnull String linkCodeHash,
            PasswordlessCode code) throws StorageQueryException, DuplicateDeviceIdHashException,
            DuplicateCodeIdException, DuplicateLinkCodeHashException;

    void createCode(PasswordlessCode code)
            throws StorageQueryException, UnknownDeviceIdHash, DuplicateCodeIdException, DuplicateLinkCodeHashException;

    void createUser(UserInfo user) throws StorageQueryException, DuplicateEmailException, DuplicatePhoneNumberException,
            DuplicateUserIdException;

    PasswordlessDevice getDevice(String deviceIdHash) throws StorageQueryException;

    PasswordlessDevice[] getDevicesByEmail(@Nonnull String email) throws StorageQueryException;

    PasswordlessDevice[] getDevicesByPhoneNumber(@Nonnull String phoneNumber) throws StorageQueryException;

    PasswordlessCode[] getCodesOfDevice(String deviceIdHash) throws StorageQueryException;

    PasswordlessCode[] getCodesBefore(long time) throws StorageQueryException;

    PasswordlessCode getCode(String codeId) throws StorageQueryException;

    PasswordlessCode getCodeByLinkCodeHash(String linkCode) throws StorageQueryException;

    UserInfo getUserById(String userId) throws StorageQueryException;

    UserInfo getUserByEmail(@Nonnull String email) throws StorageQueryException;

    UserInfo getUserByPhoneNumber(@Nonnull String phoneNumber) throws StorageQueryException;
}