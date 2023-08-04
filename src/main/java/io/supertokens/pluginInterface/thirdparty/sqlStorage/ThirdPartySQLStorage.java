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

package io.supertokens.pluginInterface.thirdparty.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.thirdparty.ThirdPartyStorage;

public interface ThirdPartySQLStorage extends ThirdPartyStorage, SQLStorage {

    void updateUserEmail_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String thirdPartyId,
                                     String thirdPartyUserId,
                                     String newEmail) throws StorageQueryException;

    void deleteThirdPartyUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                          boolean deleteUserIdMappingToo)
            throws StorageQueryException;
}
