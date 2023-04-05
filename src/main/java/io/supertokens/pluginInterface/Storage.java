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
 *
 */

package io.supertokens.pluginInterface;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.exceptions.DbInitException;
import io.supertokens.pluginInterface.exceptions.InvalidConfigException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;

import java.util.Set;

public interface Storage {

    // if silent is true, do not log anything out on the console
    void constructor(String processId, boolean silent);

    void loadConfig(JsonObject jsonConfig, Set<LOG_LEVEL> logLevels) throws InvalidConfigException;

    // this returns a unique ID based on the db's connection URI and table prefix such that
    // two different user pool IDs imply that the data for those two user pools are completely isolated.
    String getUserPoolId();

    // this returns a unique ID based on the db's connection pool config. This can be different
    // even if the getUserPoolId returns the same ID - based on the config provided by the user.
    // So two different db connection pools may point to the same end user pool.
    String getConnectionPoolId();

    // if the input otherConfig has different values set for the same properties as this user pool's config,
    // then this function should throw an error since this is a misconfig from ther user's side.
    void assertThatConfigFromSameUserPoolIsNotConflicting(JsonObject otherConfig) throws InvalidConfigException;

    void initFileLogging(String infoLogPath, String errorLogPath);

    void stopLogging();

    // load tables and create connection pools
    void initStorage() throws DbInitException;

    // used by the core to do transactions the right way.
    STORAGE_TYPE getType();

    // to be used for testing purposes only.
    void deleteAllInformation() throws StorageQueryException;

    void close();

    KeyValueInfo getKeyValue(TenantIdentifier tenantIdentifier, String key) throws StorageQueryException;

    void setKeyValue(TenantIdentifier tenantIdentifier, String key, KeyValueInfo info) throws StorageQueryException;

    void setStorageLayerEnabled(boolean enabled);

    boolean canBeUsed(JsonObject configJson);

    // this function will be used in the createUserIdMapping and deleteUserIdMapping functions to check if the userId is
    // being used in NonAuth recipes.
    boolean isUserIdBeingUsedInNonAuthRecipe(AppIdentifier appIdentifier, String className, String userId)
            throws StorageQueryException;

    // to be used for testing purposes only. This function will add dummy data to non-auth tables.
    void addInfoToNonAuthRecipesBasedOnUserId(String className, String userId) throws StorageQueryException;

    // this function is used during testing in the core so that the core can
    // create multiple user pools across any plugin being used.
    void modifyConfigToAddANewUserPoolForTesting(JsonObject config, int poolNumber);
}
