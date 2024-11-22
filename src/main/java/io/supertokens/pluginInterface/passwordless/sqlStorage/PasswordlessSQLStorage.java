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

package io.supertokens.pluginInterface.passwordless.sqlStorage;

import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.passwordless.PasswordlessCode;
import io.supertokens.pluginInterface.passwordless.PasswordlessDevice;
import io.supertokens.pluginInterface.passwordless.PasswordlessImportUser;
import io.supertokens.pluginInterface.passwordless.PasswordlessStorage;
import io.supertokens.pluginInterface.passwordless.exception.DuplicatePhoneNumberException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface PasswordlessSQLStorage extends PasswordlessStorage, SQLStorage {
    PasswordlessDevice getDevice_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                             String deviceIdHash)
            throws StorageQueryException;

    void incrementDeviceFailedAttemptCount_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                       String deviceIdHash)
            throws StorageQueryException;

    PasswordlessCode[] getCodesOfDevice_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                    String deviceIdHash)
            throws StorageQueryException;

    void deleteDevice_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String deviceIdHash)
            throws StorageQueryException;

    // we have deleteDevicesBy* for tenantIdentifier and for appIdentifier cause the tenantIdentifier version is
    // used when trying to log into one specific tenant. But if the user's detail is updated, then this
    // would affect all the tenants that share that userId.

    void deleteDevicesByPhoneNumber_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                String phoneNumber)
            throws StorageQueryException;

    void deleteDevicesByEmail_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String email)
            throws StorageQueryException;

    void deleteDevicesByPhoneNumber_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                String phoneNumber, String userId)
            throws StorageQueryException;

    void deleteDevicesByEmail_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String email,
                                          String userId)
            throws StorageQueryException;

    PasswordlessCode getCodeByLinkCodeHash_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                       String linkCodeHash)
            throws StorageQueryException;

    void deleteCode_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String codeId)
            throws StorageQueryException;

    void updateUserEmail_Transaction(AppIdentifier appIdentifier, TransactionConnection con, @Nonnull String userId,
                                     @Nullable String email)
            throws StorageQueryException, UnknownUserIdException, DuplicateEmailException;

    void updateUserPhoneNumber_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                           @Nonnull String userId,
                                           @Nullable String phoneNumber)
            throws StorageQueryException, UnknownUserIdException, DuplicatePhoneNumberException;

    void deletePasswordlessUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                            boolean deleteUserIdMappingToo)
            throws StorageQueryException;

    void importPasswordlessUsers_Transaction(TransactionConnection con, List<PasswordlessImportUser> users)
            throws StorageQueryException, TenantOrAppNotFoundException;
}
