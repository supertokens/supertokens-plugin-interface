/*
 *    Copyright (c) 2022, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.userroles;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.userroles.exception.DuplicateUserRoleMappingException;
import io.supertokens.pluginInterface.userroles.exception.UnknownRoleException;

public interface UserRolesStorage extends NonAuthRecipeStorage {

    // associate a userId with a role that exists
    void addRoleToUser(TenantIdentifier tenantIdentifier, String userId, String role)
            throws StorageQueryException, UnknownRoleException, DuplicateUserRoleMappingException,
            TenantOrAppNotFoundException;

    // get all roles associated with the input userId
    String[] getRolesForUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    // get all users associated with the input role
    String[] getUsersForRole(TenantIdentifier tenantIdentifier, String role) throws StorageQueryException;

    // get permissions associated with the input role
    String[] getPermissionsForRole(AppIdentifier appIdentifier, String role) throws StorageQueryException;

    // get roles associated with the input permission
    String[] getRolesThatHavePermission(AppIdentifier appIdentifier, String permission) throws StorageQueryException;

    // delete a role
    boolean deleteRole(AppIdentifier appIdentifier, String role) throws StorageQueryException;

    // get all created roles
    String[] getRoles(AppIdentifier appIdentifier) throws StorageQueryException;

    // check if input roles exists
    boolean doesRoleExist(AppIdentifier appIdentifier, String role) throws StorageQueryException;

    // delete all roles for the input userId
    int deleteAllRolesForUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;
}
