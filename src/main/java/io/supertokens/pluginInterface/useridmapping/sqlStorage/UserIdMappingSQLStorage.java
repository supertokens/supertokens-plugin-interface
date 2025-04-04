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

package io.supertokens.pluginInterface.useridmapping.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.useridmapping.UserIdMapping;
import io.supertokens.pluginInterface.useridmapping.UserIdMappingStorage;

import java.util.List;

public interface UserIdMappingSQLStorage extends UserIdMappingStorage, SQLStorage {
    UserIdMapping getUserIdMapping_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                               boolean isSuperTokensUserId)
            throws StorageQueryException;

    UserIdMapping[] getUserIdMapping_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    List<UserIdMapping> getMultipleUserIdMapping_Transaction(TransactionConnection connection, AppIdentifier appIdentifier,
                                                             List<String> userIds, boolean isSupertokensIds)
        throws StorageQueryException;
}
