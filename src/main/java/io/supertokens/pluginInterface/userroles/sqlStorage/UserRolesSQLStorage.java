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

package io.supertokens.pluginInterface.userroles.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.userroles.UserRolesStorage;
import io.supertokens.pluginInterface.userroles.exception.UnknownRoleException;

import javax.annotation.Nullable;

public interface UserRolesSQLStorage extends UserRolesStorage, SQLStorage {

    // delete input roles associated with the input userId, if roles is null then all roles associated with the userId
    // are removed
    void deleteUserRoles_Transaction(TransactionConnection con, String userId, @Nullable String[] roles)
            throws StorageQueryException;

    // create a role with permissions / add additional permissions to a role if it already exists
    void setRole_Transaction(TransactionConnection con, String role, String[] permissions) throws StorageQueryException;

    // remove permissions associated with a role, if permissions is NULL then all permissions are removed from the role
    void deleteRolePermissions_Transaction(TransactionConnection con, String role, @Nullable String[] permissions)
            throws StorageQueryException, UnknownRoleException;
}
