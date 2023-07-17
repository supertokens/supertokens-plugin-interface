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

public interface AuthRecipeSQLStorage extends AuthRecipeStorage {

    // These are all app indetifier cause they are used in account linking which link users
    // across tenants anyway.
    AuthRecipeUserInfo getPrimaryUserById_Transaction(AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    // lock order:
    // - emailpassword table
    // - thirdparty table
    // - passwordless table
    AuthRecipeUserInfo[] listPrimaryUsersByEmail_Transaction(AppIdentifier appIdentifier, String email)
            throws StorageQueryException;

    // locks only passwordless table
    AuthRecipeUserInfo[] listPrimaryUsersByPhoneNumber_Transaction(AppIdentifier appIdentifier, String phoneNumber)
            throws StorageQueryException;

    // locks on thirdparty table
    AuthRecipeUserInfo getPrimaryUserByThirdPartyInfo_Transaction(AppIdentifier appIdentifier, String thirdPartyId,
                                                                  String thirdPartyUserId) throws StorageQueryException;
}
