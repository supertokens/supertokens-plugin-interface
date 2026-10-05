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

    public void saveRelayStateInfo(TenantIdentifier tenantIdentifier, SAMLRelayStateInfo relayStateInfo, long relayStateValidity) throws StorageQueryException;
    // GHSA-3r7x-rhvc-fw28: reads the RelayState and deletes it atomically, so a captured
    // RelayState (and the SAMLResponse submitted alongside it) is usable at most once and a
    // replayed callback request cannot mint further authorization codes.
    public SAMLRelayStateInfo getRelayStateInfoAndRemove(TenantIdentifier tenantIdentifier, String relayState) throws StorageQueryException;

    public void saveSAMLClaims(TenantIdentifier tenantIdentifier, String clientId, String code, JsonObject claims, long claimsValidity) throws StorageQueryException;
    public SAMLClaimsInfo getSAMLClaimsAndRemoveCode(TenantIdentifier tenantIdentifier, String code) throws StorageQueryException;

    // Records a consumed SAML assertion ID so it can never be accepted twice. Returns true if this
    // call inserted the row (the assertion had not been seen), false if a row already existed for it
    // (a replay). The insert-if-absent and the answer are one atomic operation, so two concurrent
    // replays of the same assertion cannot both observe it as new. expiresAt is the point after which
    // the assertion is no longer valid, so the row can be purged. See GHSA-3r7x-rhvc-fw28.
    public boolean markSAMLAssertionConsumed(TenantIdentifier tenantIdentifier, String assertionId, long expiresAt) throws StorageQueryException;

    public void removeExpiredSAMLCodesAndRelayStates() throws StorageQueryException;
    public int countSAMLClients(TenantIdentifier tenantIdentifier) throws StorageQueryException;
}
