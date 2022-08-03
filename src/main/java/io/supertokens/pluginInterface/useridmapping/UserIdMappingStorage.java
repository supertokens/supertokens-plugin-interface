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
import io.supertokens.pluginInterface.useridmapping.exception.UnknownSuperTokensUserIdException;
import io.supertokens.pluginInterface.useridmapping.exception.UserIdMappingAlreadyExistsException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserIdMappingStorage extends Storage {

    void createUserIdMapping(String superTokensUserId, String externalUserId, @Nullable String externalUserIdInfo)
            throws StorageQueryException, UnknownSuperTokensUserIdException, UserIdMappingAlreadyExistsException;

    boolean deleteUserIdMapping(String userId, boolean isSuperTokensUserId) throws StorageQueryException;

    UserIdMapping getUserIdMapping(String userId, boolean isSuperTokensUserId) throws StorageQueryException;

    UserIdMapping[] getUserIdMapping(String userId) throws StorageQueryException;

    boolean updateOrDeleteExternalUserIdInfo(String userId, boolean isSuperTokensUserId,
            @Nullable String externalUserIdInfo) throws StorageQueryException;

    // This function will be used in the user pagination API to retrieve the userId mapping for a list of userIds, so we
    // can set the externalUserId in the response if a mapping exists. The key of the HashMap will be the
    // superTokensUserId and the value will be the externalUserId
    HashMap<String, String> getUserIdMappingForSuperTokensIds(ArrayList<String> userIds) throws StorageQueryException;

}
