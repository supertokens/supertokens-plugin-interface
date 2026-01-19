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

import java.util.List;
import java.util.Map;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;
import io.supertokens.pluginInterface.authRecipe.CanBecomePrimaryResult;
import io.supertokens.pluginInterface.authRecipe.CanLinkAccountsResult;
import io.supertokens.pluginInterface.authRecipe.exceptions.*;
import io.supertokens.pluginInterface.bulkimport.PrimaryUser;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface AuthRecipeSQLStorage extends AuthRecipeStorage, SQLStorage {

    AuthRecipeUserInfo getPrimaryUserById_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                      String userId)
            throws StorageQueryException;

    AuthRecipeUserInfo getPrimaryUserByWebauthNCredentialId_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                                        String credentialId)
            throws StorageQueryException;

    // locks on thirdparty table
    AuthRecipeUserInfo[] listPrimaryUsersByThirdPartyInfo_Transaction(AppIdentifier appIdentifier,
                                                                      TransactionConnection con, String thirdPartyId,
                                                                      String thirdPartyUserId)
            throws StorageQueryException;

    boolean makePrimaryUser_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException, UnknownUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException,
            CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException;

    boolean linkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String recipeUserId,
                                  String primaryUserId) throws StorageQueryException, UnknownUserIdException,
            InputUserIdIsNotAPrimaryUserException, CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;

    void unlinkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String primaryUserId,
                                    String recipeUserId)
            throws StorageQueryException;

    boolean doesUserIdExist_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String externalUserId)
            throws StorageQueryException;

    CanBecomePrimaryResult checkIfLoginMethodCanBecomePrimary(AppIdentifier appIdentifier, String recipeUserId) throws
            StorageQueryException, UnknownUserIdException; // TODO move this to regular storage, not SQLStorage

    CanLinkAccountsResult checkIfLoginMethodsCanBeLinked(AppIdentifier appIdentifier,
                                                         String primaryUserId, String recipeUserId) throws
            StorageQueryException, UnknownUserIdException; // TODO move this to regular storage, not SQLStorage

    void addTenantIdToPrimaryUser_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String supertokensUserId)
            throws AnotherPrimaryUserWithPhoneNumberAlreadyExistsException,
            AnotherPrimaryUserWithEmailAlreadyExistsException,
            AnotherPrimaryUserWithThirdPartyInfoAlreadyExistsException, StorageQueryException;

    void deleteAccountInfoReservations_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    void reservePrimaryUserAccountInfos_Transaction(TransactionConnection con, List<PrimaryUser> primaryUsers)
            throws StorageQueryException, StorageTransactionLogicException;
}
