/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.saml;

import java.util.List;

import com.google.gson.JsonObject;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.saml.exception.DuplicateEntityIdException;

public interface SAMLStorage extends NonAuthRecipeStorage {
    public SAMLClient createOrUpdateSAMLClient(TenantIdentifier tenantIdentifier, SAMLClient samlClient) throws StorageQueryException, DuplicateEntityIdException;
    public boolean removeSAMLClient(TenantIdentifier tenantIdentifier, String clientId) throws StorageQueryException;
    public SAMLClient getSAMLClient(TenantIdentifier tenantIdentifier, String clientId) throws StorageQueryException;
    public SAMLClient getSAMLClientByIDPEntityId(TenantIdentifier tenantIdentifier, String idpEntityId) throws StorageQueryException;
    public List<SAMLClient> getSAMLClients(TenantIdentifier tenantIdentifier) throws StorageQueryException;

    public void saveRelayStateInfo(TenantIdentifier tenantIdentifier, SAMLRelayStateInfo relayStateInfo) throws StorageQueryException;
    public SAMLRelayStateInfo getRelayStateInfo(TenantIdentifier tenantIdentifier, String relayState) throws StorageQueryException;

    public void saveSAMLClaims(TenantIdentifier tenantIdentifier, String clientId, String code, JsonObject claims) throws StorageQueryException;
    public SAMLClaimsInfo getSAMLClaimsAndRemoveCode(TenantIdentifier tenantIdentifier, String code) throws StorageQueryException;

    public void removeExpiredSAMLCodesAndRelayStates() throws StorageQueryException;
    public int countSAMLClients(TenantIdentifier tenantIdentifier) throws StorageQueryException;
}
