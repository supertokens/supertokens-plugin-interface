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

package io.supertokens.pluginInterface.multitenancy;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.emailpassword.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateTenantException;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.userroles.exception.UnknownRoleException;

public interface MultitenancyStorage extends Storage {

    void createTenant(TenantConfig config) throws DuplicateTenantException, StorageQueryException;

    // this adds tenantId to the target user pool
    void addTenantIdInUserPool(TenantIdentifier tenantIdentifier) throws DuplicateTenantException, StorageQueryException;

    // this also deletes all tenant info from all tables.
    void deleteTenantIdInUserPool(TenantIdentifier tenantIdentifier) throws TenantOrAppNotFoundException;

    void overwriteTenantConfig(TenantConfig config) throws TenantOrAppNotFoundException, StorageQueryException;

    void deleteTenant(TenantIdentifier tenantIdentifier) throws TenantOrAppNotFoundException;

    void deleteApp(TenantIdentifier tenantIdentifier) throws TenantOrAppNotFoundException;

    void deleteConnectionUriDomainMapping(TenantIdentifier tenantIdentifier) throws TenantOrAppNotFoundException;

    TenantConfig[] getAllTenants() throws StorageQueryException;

    void addUserIdToTenant(TenantIdentifier tenantIdentifier, String userId) throws TenantOrAppNotFoundException,
            UnknownUserIdException;

    void addRoleToTenant(TenantIdentifier tenantIdentifier, String role) throws TenantOrAppNotFoundException,
            UnknownRoleException;

    void deleteAppId(String appId) throws TenantOrAppNotFoundException;

    void deleteConnectionUriDomain(String connectionUriDomain) throws TenantOrAppNotFoundException;

}