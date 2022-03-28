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

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.userroles.exception.UnknownRoleException;

public interface UserRolesStorage extends Storage {

    // associate a userId with a role that exists
    void setUserRole(String userId, String role) throws StorageQueryException, UnknownRoleException;

    // get all roles associated with the input userId
    String[] getUserRoles(String userId) throws StorageQueryException;

    // get all users associated with the input role
    String[] getRoleUsers(String role) throws StorageQueryException;

    // get permissions associated with the input role
    String[] getRolePermissions(String role) throws StorageQueryException, UnknownRoleException;

    // get roles associated with the input permission
    String[] getPermissionRoles(String permission) throws StorageQueryException;

    // delete a role
    void deleteRole(String role) throws StorageQueryException;

    // get all created roles
    String[] getRoles() throws StorageQueryException;
}
