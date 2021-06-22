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

package io.supertokens.pluginInterface.thirdparty;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.thirdparty.exception.DuplicateThirdPartyUserException;
import io.supertokens.pluginInterface.thirdparty.exception.DuplicateUserIdException;

import javax.annotation.Nonnull;

public interface ThirdPartyStorage extends Storage {

    void signUp(UserInfo userInfo)
            throws StorageQueryException, DuplicateUserIdException, DuplicateThirdPartyUserException;

    UserInfo getThirdPartyUserInfoUsingId(String thirdPartyId, String thirdPartyUserId)
            throws StorageQueryException;

    UserInfo getThirdPartyUserInfoUsingId(String userId) throws StorageQueryException;

    @Deprecated
    UserInfo[] getThirdPartyUsers(@Nonnull String userId, @Nonnull Long timeJoined, @Nonnull Integer limit,
                                  @Nonnull String timeJoinedOrder) throws StorageQueryException;

    @Deprecated
    UserInfo[] getThirdPartyUsers(@Nonnull Integer limit, @Nonnull String timeJoinedOrder) throws StorageQueryException;

    @Deprecated
    long getThirdPartyUsersCount() throws StorageQueryException;

}