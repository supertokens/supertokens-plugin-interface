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

import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public interface Storage {

    // if silent is true, do not log anything out on the console
    void constructor(String processId, boolean silent);

    void loadConfig(String configFilePath);

    void initFileLogging(String infoLogPath, String errorLogPath);

    void stopLogging();

    // load tables and create connection pools
    void initStorage();


    // used by the core to do transactions the right way.
    STORAGE_TYPE getType();

    // to be used for testing purposes only.
    void deleteAllInformation() throws StorageQueryException;

    void close();

    KeyValueInfo getKeyValue(String key) throws StorageQueryException;

    void setKeyValue(String key, KeyValueInfo info) throws StorageQueryException;

    void setStorageLayerEnabled(boolean enabled);

    boolean canBeUsed(String configFilePath);

    long getUsersCount(String[] includeRecipeIds) throws StorageQueryException;
}
