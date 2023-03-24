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
import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.emailpassword.sqlStorage.EmailPasswordSQLStorage;
import io.supertokens.pluginInterface.emailverification.sqlStorage.EmailVerificationSQLStorage;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.session.SessionStorage;
import io.supertokens.pluginInterface.useridmapping.UserIdMappingStorage;
import io.supertokens.pluginInterface.usermetadata.sqlStorage.UserMetadataSQLStorage;
import io.supertokens.pluginInterface.userroles.sqlStorage.UserRolesSQLStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AppIdentifier {
    public static final String DEFAULT_APP_ID = "public";
    public static final String DEFAULT_CONNECTION_URI = "";

    @Nullable
    private final String connectionUriDomain;

    @Nullable
    private final String appId;

    public AppIdentifier(@Nullable String connectionUriDomain, @Nullable String appId) {
        this.connectionUriDomain = connectionUriDomain;
        this.appId = appId;
    }

    @Nonnull
    public String getAppId() {
        if (this.appId == null || this.appId.equals("")) {
            return DEFAULT_APP_ID;
        }
        return this.appId.trim().toLowerCase();
    }

    @Nonnull
    public String getConnectionUriDomain() {
        if (this.connectionUriDomain == null) {
            return DEFAULT_CONNECTION_URI;
        }
        return this.connectionUriDomain.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AppIdentifier) {
            AppIdentifier otherAppIdentifier = (AppIdentifier) other;
            return otherAppIdentifier.getConnectionUriDomain().equals(this.getConnectionUriDomain()) &&
                    otherAppIdentifier.getAppId().equals(this.getAppId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.getConnectionUriDomain() + "|" +
                this.getAppId()).hashCode();
    }

    public TenantIdentifier getAsPublicTenantIdentifier() {
        return new TenantIdentifier(this.getConnectionUriDomain(), this.getAppId(), null);
    }

    public AppIdentifierWithStorage withStorage(Storage storage) {
        return new AppIdentifierWithStorage(this.getConnectionUriDomain(), this.getAppId(), storage);
    }
}
