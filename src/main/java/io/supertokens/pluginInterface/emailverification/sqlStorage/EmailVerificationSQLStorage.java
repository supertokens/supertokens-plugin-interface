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

package io.supertokens.pluginInterface.emailverification.sqlStorage;

import io.supertokens.pluginInterface.emailverification.EmailVerificationStorage;
import io.supertokens.pluginInterface.emailverification.EmailVerificationTokenInfo;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

import java.util.Map;

public interface EmailVerificationSQLStorage extends EmailVerificationStorage, SQLStorage {

    EmailVerificationTokenInfo[] getAllEmailVerificationTokenInfoForUser_Transaction(TenantIdentifier tenantIdentifier,
                                                                                     TransactionConnection con,
                                                                                     String userId, String email)
            throws StorageQueryException;

    void deleteAllEmailVerificationTokensForUser_Transaction(TenantIdentifier tenantIdentifier,
                                                             TransactionConnection con,
                                                             String userId, String email)
            throws StorageQueryException;

    void updateIsEmailVerified_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId,
                                           String email,
                                           boolean isEmailVerified)
            throws StorageQueryException, TenantOrAppNotFoundException;

    void updateMultipleIsEmailVerified_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                   Map<String, String> emailToUserId, boolean isEmailVerified)
            throws StorageQueryException, TenantOrAppNotFoundException;

    void deleteEmailVerificationUserInfo_Transaction(TransactionConnection con, AppIdentifier appIdentifier,
                                                     String userId) throws StorageQueryException;

}
