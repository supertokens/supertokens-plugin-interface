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

package io.supertokens.pluginInterface.usermetadata.sqlStorage;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.usermetadata.UserMetadataStorage;

import java.util.List;
import java.util.Map;

public interface UserMetadataSQLStorage extends UserMetadataStorage, SQLStorage {
    JsonObject getUserMetadata_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException;

    Map<String, JsonObject> getMultipleUsersMetadatas_Transaction(AppIdentifier appIdentifier, TransactionConnection
            con, List<String> userIds)
            throws StorageQueryException;

    int setUserMetadata_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId,
                                    JsonObject metadata)
            throws StorageQueryException, TenantOrAppNotFoundException;

    void setMultipleUsersMetadatas_Transaction(AppIdentifier appIdentifier, TransactionConnection con, Map<String, JsonObject> metadataByUserId)
            throws StorageQueryException, TenantOrAppNotFoundException;

    int deleteUserMetadata_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;
}
