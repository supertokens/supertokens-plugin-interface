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
import io.supertokens.pluginInterface.emailpassword.UserInfo;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.SessionObject;

public interface EmailPasswordSQLStorage extends EmailPasswordStorage, SQLStorage {

    PasswordResetTokenInfo[] getAllPasswordResetTokenInfoForUser_Transaction(SessionObject sessionInstance,
            String userId) throws StorageQueryException;

    void deleteAllPasswordResetTokensForUser_Transaction(SessionObject sessionInstance, String userId)
            throws StorageQueryException;

    void updateUsersPassword_Transaction(SessionObject sessionInstance, String userId, String newPassword)
            throws StorageQueryException;

    void updateUsersEmail_Transaction(SessionObject sessionInstance, String userId, String email)
            throws StorageQueryException, DuplicateEmailException;

    UserInfo getUserInfoUsingId_Transaction(SessionObject sessionInstance, String userId) throws StorageQueryException;

}
