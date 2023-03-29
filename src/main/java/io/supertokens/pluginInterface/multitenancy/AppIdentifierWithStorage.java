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

package io.supertokens.pluginInterface.multitenancy;

import io.supertokens.pluginInterface.STORAGE_TYPE;
import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.useridmapping.UserIdMappingStorage;
import io.supertokens.pluginInterface.usermetadata.sqlStorage.UserMetadataSQLStorage;
import io.supertokens.pluginInterface.userroles.sqlStorage.UserRolesSQLStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AppIdentifierWithStorage extends AppIdentifier {

    @Nonnull
    private final Storage storage;

    private final Storage[] storages;

    public AppIdentifierWithStorage(@Nullable String connectionUriDomain, @Nullable String appId, @Nonnull
    Storage storage) {
        super(connectionUriDomain, appId);
        this.storage = storage;
        this.storages = new Storage[]{storage};
    }

    public AppIdentifierWithStorage(@Nullable String connectionUriDomain, @Nullable String appId, @Nonnull
    Storage storage, @Nonnull Storage[] storages) {
        super(connectionUriDomain, appId);
        this.storage = storage;
        this.storages = storages;
    }

    @Nonnull
    public Storage getStorage() {
        return storage;
    }

    @Nonnull
    public Storage[] getStorages() {
        return storages;
    }

    public UserMetadataSQLStorage getUserMetadataStorage() {
        if (this.storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (UserMetadataSQLStorage) this.storage;
    }

    public UserIdMappingStorage getUserIdMappingStorage() {
        if (this.storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }

        return (UserIdMappingStorage) this.storage;
    }

    public UserRolesSQLStorage getUserRolesStorage() {
        if (this.storage.getType() != STORAGE_TYPE.SQL) {
            // we only support SQL for now
            throw new UnsupportedOperationException("");
        }
        return (UserRolesSQLStorage) this.storage;
    }
}
