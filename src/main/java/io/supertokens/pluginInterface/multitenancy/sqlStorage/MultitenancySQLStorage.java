/*
 *    Copyright (c) 2023, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.multitenancy.sqlStorage;

import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.authRecipe.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.multitenancy.MultitenancyStorage;
import io.supertokens.pluginInterface.multitenancy.TenantConfig;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateClientTypeException;
import io.supertokens.pluginInterface.multitenancy.exceptions.DuplicateThirdPartyIdException;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.passwordless.exception.DuplicatePhoneNumberException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.thirdparty.exception.DuplicateThirdPartyUserException;

import javax.annotation.Nullable;

public interface MultitenancySQLStorage extends MultitenancyStorage, SQLStorage {
    boolean addUserIdToTenant_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException, TenantOrAppNotFoundException, DuplicateEmailException,
            DuplicateThirdPartyUserException, DuplicatePhoneNumberException, UnknownUserIdException;

    // Connection-taking counterpart of removeUserIdFromTenant: same writes and the same row locking as the
    // auto-commit version, but performed on the caller's connection so the tenant disassociation and its
    // lifecycle audit event can be committed (or rolled back) together by the caller. Returns whether a row
    // was actually removed.
    boolean removeUserIdFromTenant_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                               String userId)
            throws StorageQueryException;

    // The stored core config of exactly this tenant (no inheritance), or null if the tenant has no row. Reads the
    // committed row on the caller's connection, so a caller holding a lock reads the value that lock protects.
    @Nullable
    JsonObject getTenantCoreConfig_Transaction(TransactionConnection con, TenantIdentifier tenantIdentifier)
            throws StorageQueryException;

    // Connection-taking counterpart of overwriteTenantConfig: same writes and errors, committed (or rolled back)
    // with the caller's transaction.
    void overwriteTenantConfig_Transaction(TransactionConnection con, TenantConfig config)
            throws TenantOrAppNotFoundException, DuplicateThirdPartyIdException, DuplicateClientTypeException,
            StorageQueryException;
}
