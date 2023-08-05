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
 *
 */

package io.supertokens.pluginInterface.authRecipe;

import io.supertokens.pluginInterface.RECIPE_ID;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.dashboard.DashboardSearchTags;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface AuthRecipeStorage extends Storage {

    long getUsersCount(TenantIdentifier tenantIdentifier, @Nullable RECIPE_ID[] includeRecipeIds)
            throws StorageQueryException;

    long getUsersCount(AppIdentifier appIdentifier, @Nullable RECIPE_ID[] includeRecipeIds)
            throws StorageQueryException;

    AuthRecipeUserInfo[] getUsers(TenantIdentifier tenantIdentifier, @Nonnull Integer limit,
                                  @Nonnull String timeJoinedOrder,
                                  @Nullable RECIPE_ID[] includeRecipeIds, @Nullable String userId,
                                  @Nullable Long timeJoined, @Nullable DashboardSearchTags dashboardSearchTags)
            throws StorageQueryException;

    boolean doesUserIdExist(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    boolean doesUserIdExist(TenantIdentifier tenantIdentifierIdentifier, String userId) throws StorageQueryException;

    AuthRecipeUserInfo getPrimaryUserById(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    String getPrimaryUserIdStrForUserId(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    AuthRecipeUserInfo[] listPrimaryUsersByEmail(TenantIdentifier tenantIdentifier, String email)
            throws StorageQueryException;

    AuthRecipeUserInfo[] listPrimaryUsersByPhoneNumber(TenantIdentifier tenantIdentifier, String phoneNumber)
            throws StorageQueryException;

    AuthRecipeUserInfo getPrimaryUserByThirdPartyInfo(TenantIdentifier tenantIdentifier, String thirdPartyId,
                                                      String thirdPartyUserId) throws StorageQueryException;
}
