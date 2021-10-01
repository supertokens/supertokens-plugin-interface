/*
 *    Copyright (c) 2021, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.jwt.nosqlstorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.jwt.JWTRecipeStorage;
import io.supertokens.pluginInterface.jwt.JWTSigningKeyInfo;
import io.supertokens.pluginInterface.jwt.exceptions.DuplicateKeyIdException;
import io.supertokens.pluginInterface.noSqlStorage.NoSQLStorage_1;

import java.util.List;

public interface JWTRecipeNoSQLStorage_1 extends JWTRecipeStorage, NoSQLStorage_1 {
    List<JWTSigningKeyInfo> getJWTSigningKeys_Transaction() throws StorageQueryException;

    /**
     * This function inserts a key into storage if there are no keys available for a
     * given algorithm (RSA, EC etc)
     */
    boolean setJWTSigningKeyInfoIfNoKeyForAlgorithmExists_Transaction(JWTSigningKeyInfo keyInfo)
            throws StorageQueryException, DuplicateKeyIdException;
}
