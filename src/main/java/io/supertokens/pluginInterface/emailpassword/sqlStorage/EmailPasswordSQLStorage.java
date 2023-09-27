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

package io.supertokens.pluginInterface.emailpassword.sqlStorage;

import io.supertokens.pluginInterface.emailpassword.EmailPasswordStorage;
import io.supertokens.pluginInterface.emailpassword.PasswordResetTokenInfo;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface EmailPasswordSQLStorage extends EmailPasswordStorage, SQLStorage {

    // all password reset related stuff is app wide cause the same user ID can be shared across tenants,
    // and updating / resetting a user's password should apply to all those tenants.

    PasswordResetTokenInfo[] getAllPasswordResetTokenInfoForUser_Transaction(AppIdentifier appIdentifier,
                                                                             TransactionConnection con, String userId)
            throws StorageQueryException;

    void deleteAllPasswordResetTokensForUser_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                         String userId)
            throws StorageQueryException;

    void updateUsersPassword_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId,
                                         String newPassword)
            throws StorageQueryException;

    void updateUsersEmail_Transaction(AppIdentifier appIdentifier, TransactionConnection conn, String userId,
                                      String email)
            throws StorageQueryException, DuplicateEmailException;

    // this deletion of a user is app wide since the same user ID can be shared across tenants
    void deleteEmailPasswordUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                             boolean deleteUserIdMappingToo)
            throws StorageQueryException;
}
