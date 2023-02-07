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
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateTenantException;
import io.supertokens.pluginInterface.multitenancy.exceptions.UnknownTenantException;

public interface MultitenancyStorage extends Storage {

    void createTenant(TenantConfig config) throws DuplicateTenantException;

    void overwriteTenantConfig(TenantConfig config) throws UnknownTenantException;

    void deleteTenant(TenantIdentifier tenantIdentifier) throws UnknownTenantException;

    void deleteApp(TenantIdentifier tenantIdentifier) throws UnknownTenantException;

    void deleteConnectionUriDomainMapping(TenantIdentifier tenantIdentifier) throws UnknownTenantException;

    TenantConfig getTenantConfigForTenantIdentifier(TenantIdentifier tenantIdentifier);

    TenantConfig[] getAllTenants();

    TenantConfig[] getAllTenantsWithThirdPartyId(String thirdPartyId);

}