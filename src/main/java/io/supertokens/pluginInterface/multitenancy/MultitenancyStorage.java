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
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateClientTypeException;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateTenantException;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateThirdPartyIdException;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.passwordless.exception.DuplicatePhoneNumberException;
import io.supertokens.pluginInterface.thirdparty.exception.DuplicateThirdPartyUserException;
import io.supertokens.pluginInterface.userroles.exception.UnknownRoleException;

public interface MultitenancyStorage extends Storage {

    void createTenant(TenantConfig config) throws DuplicateTenantException, DuplicateThirdPartyIdException,
            DuplicateClientTypeException, StorageQueryException;

    // this adds tenantId to the target user pool
    void addTenantIdInTargetStorage(TenantIdentifier tenantIdentifier)
            throws DuplicateTenantException, StorageQueryException;

    // this also deletes all tenant info from all tables.
    void deleteTenantIdInTargetStorage(TenantIdentifier tenantIdentifier)
            throws TenantOrAppNotFoundException, StorageQueryException;

    void overwriteTenantConfig(TenantConfig config) throws TenantOrAppNotFoundException, 
            DuplicateThirdPartyIdException, DuplicateClientTypeException, StorageQueryException;

    boolean deleteTenantInfoInBaseStorage(TenantIdentifier tenantIdentifier) throws StorageQueryException;

    boolean deleteAppInfoInBaseStorage(AppIdentifier appIdentifier) throws StorageQueryException;

    boolean deleteConnectionUriDomainInfoInBaseStorage(String connectionUriDomain) throws StorageQueryException;

    TenantConfig[] getAllTenants() throws StorageQueryException;

    boolean addUserIdToTenant(TenantIdentifier tenantIdentifier, String userId) throws TenantOrAppNotFoundException,
            UnknownUserIdException, StorageQueryException, DuplicateEmailException, DuplicateThirdPartyUserException,
            DuplicatePhoneNumberException;

    boolean removeUserIdFromTenant(TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException, UnknownUserIdException;
}