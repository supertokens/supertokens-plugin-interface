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
import io.supertokens.pluginInterface.useridmapping.exception.UnknownMappingException;
import io.supertokens.pluginInterface.useridmapping.exception.UnknownSuperTokensUserIdException;

public interface UserIdMappingStorage extends Storage {

    UserIdMappingExistsInfo createUserIdMapping(String superTokensUserId, String externalUserId)
            throws StorageQueryException, UnknownSuperTokensUserIdException;

    boolean deleteUserIdMapping(String usersId, Boolean isSuperTokensUserId) throws StorageQueryException;

    UserIdMappingInfo[] getUserIdMapping(String userId, String userIdType)
            throws StorageQueryException, UnknownMappingException;

    void updateExternalUserIdInfo(String userId, String userIdType, String externalUserIdInfo)
            throws StorageQueryException, UnknownMappingException;

    boolean isSuperTokensUserId(String userId) throws StorageQueryException;

}
