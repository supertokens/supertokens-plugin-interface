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

package io.supertokens.pluginInterface.authRecipe.sqlStorage;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface AuthRecipeSQLStorage extends AuthRecipeStorage, SQLStorage {

    AuthRecipeUserInfo getPrimaryUserById_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                      String userId)
            throws StorageQueryException;

    // lock order:
    // - emailpassword table
    // - thirdparty table
    // - passwordless table
    AuthRecipeUserInfo[] listPrimaryUsersByEmail_Transaction(AppIdentifier appIdentifier,
                                                             TransactionConnection con,
                                                             String email)
            throws StorageQueryException;

    // locks only passwordless table
    AuthRecipeUserInfo[] listPrimaryUsersByPhoneNumber_Transaction(AppIdentifier appIdentifier,
                                                                   TransactionConnection con, String phoneNumber)
            throws StorageQueryException;

    // locks on thirdparty table
    AuthRecipeUserInfo[] listPrimaryUsersByThirdPartyInfo_Transaction(AppIdentifier appIdentifier,
                                                                      TransactionConnection con, String thirdPartyId,
                                                                      String thirdPartyUserId)
            throws StorageQueryException;

    void makePrimaryUser_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException;

    void linkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String recipeUserId,
                                  String primaryUserId) throws StorageQueryException;

    void unlinkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String primaryUserId, String recipeUserId)
            throws StorageQueryException;

    boolean doesUserIdExist_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String externalUserId) throws StorageQueryException;
}
