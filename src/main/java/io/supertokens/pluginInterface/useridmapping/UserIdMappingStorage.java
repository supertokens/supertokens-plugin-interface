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

package io.supertokens.pluginInterface.useridmapping;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.useridmapping.exception.UnknownSuperTokensUserIdException;
import io.supertokens.pluginInterface.useridmapping.exception.UserIdMappingAlreadyExistsException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface UserIdMappingStorage extends Storage {

    // whilst these take the full tenantIdentifier as an input, they ignore the tenantId
    // cause user ID mapping is per app and not per tenant.

    void createUserIdMapping(AppIdentifier appIdentifier, String superTokensUserId, String externalUserId,
                             @Nullable String externalUserIdInfo)
            throws StorageQueryException, UnknownSuperTokensUserIdException, UserIdMappingAlreadyExistsException;

    // support method for bulk migration
    void createBulkUserIdMapping(AppIdentifier appIdentifier, Map<String,String> superTokensUserIdToExternalUserId)
            throws StorageQueryException;

    boolean deleteUserIdMapping(AppIdentifier appIdentifier, String userId, boolean isSuperTokensUserId)
            throws StorageQueryException;

    UserIdMapping getUserIdMapping(AppIdentifier appIdentifier, String userId, boolean isSuperTokensUserId)
            throws StorageQueryException;

    UserIdMapping[] getUserIdMapping(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    boolean updateOrDeleteExternalUserIdInfo(AppIdentifier appIdentifier, String userId,
                                             boolean isSuperTokensUserId,
                                             @Nullable String externalUserIdInfo) throws StorageQueryException;

    // This function will be used to retrieve the userId mapping for a list of userIds. The key of the HashMap will be
    // superTokensUserId and the value will be the externalUserId. If a mapping does not exist for an input userId,
    // it will not be in a part of the returned HashMap
    HashMap<String, String> getUserIdMappingForSuperTokensIds(AppIdentifier appIdentifier,
                                                              ArrayList<String> userIds) throws StorageQueryException;

    // Returns true if the app has at least one user id mapping
    boolean hasUserIdMappings(AppIdentifier appIdentifier) throws StorageQueryException;

    // The apps served by this storage that have disable_user_id_mapping on. For them the storage skips the user id
    // mapping reads it makes on its own (e.g. while loading users or sessions), so it never reads userid_mapping
    // for them. Each call replaces the previous set; until the first call the storage reads mappings for every app.
    void setAppsWithUserIdMappingDisabled(Set<AppIdentifier> appIdentifiers);

}
