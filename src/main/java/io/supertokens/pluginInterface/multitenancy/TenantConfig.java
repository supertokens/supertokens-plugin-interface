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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import io.supertokens.pluginInterface.Storage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TenantConfig {

    @Nonnull
    public transient final TenantIdentifier tenantIdentifier;

    @Nonnull
    @SerializedName("emailPassword")
    public final EmailPasswordConfig emailPasswordConfig;

    @Nonnull
    @SerializedName("thirdParty")
    public final ThirdPartyConfig thirdPartyConfig;

    @Nonnull
    @SerializedName("passwordless")
    public final PasswordlessConfig passwordlessConfig;

    @Nonnull
    public final JsonObject coreConfig;

    public TenantConfig(@Nonnull TenantIdentifier tenantIdentifier, @Nonnull EmailPasswordConfig emailPasswordConfig,
                        @Nonnull ThirdPartyConfig thirdPartyConfig,
                        @Nonnull PasswordlessConfig passwordlessConfig, @Nullable JsonObject coreConfig) {
        this.tenantIdentifier = tenantIdentifier;
        this.coreConfig = coreConfig == null ? new JsonObject() : coreConfig;
        this.emailPasswordConfig = emailPasswordConfig;
        this.passwordlessConfig = passwordlessConfig;
        this.thirdPartyConfig = thirdPartyConfig;
    }

    public TenantConfig(TenantConfig other) {
        // copy constructor, that does a deep copy
        Gson gson = new Gson();
        this.tenantIdentifier = new TenantIdentifier(other.tenantIdentifier.getConnectionUriDomain(), other.tenantIdentifier.getAppId(), other.tenantIdentifier.getTenantId());
        this.coreConfig = gson.fromJson(other.coreConfig.toString(), JsonObject.class);
        this.emailPasswordConfig = new EmailPasswordConfig(other.emailPasswordConfig.enabled);
        this.passwordlessConfig = new PasswordlessConfig(other.passwordlessConfig.enabled);
        this.thirdPartyConfig = gson.fromJson(gson.toJsonTree(other.thirdPartyConfig).getAsJsonObject(), ThirdPartyConfig.class);
    }

    public boolean deepEquals(TenantConfig other) {
        if (other == null) {
            return false;
        }
        return this.tenantIdentifier.equals(other.tenantIdentifier) &&
                this.emailPasswordConfig.equals(other.emailPasswordConfig) &&
                this.passwordlessConfig.equals(other.passwordlessConfig) &&
                this.thirdPartyConfig.equals(other.thirdPartyConfig) &&
                this.coreConfig.equals(other.coreConfig);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof TenantConfig) {
            TenantConfig otherTenantConfig = (TenantConfig) other;
            return otherTenantConfig.tenantIdentifier.equals(this.tenantIdentifier);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tenantIdentifier.hashCode();
    }

    public JsonObject toJson(boolean shouldProtectDbConfig, Storage storage) {
        Gson gson = new Gson();
        JsonObject tenantConfigObject = gson.toJsonTree(this).getAsJsonObject();
        tenantConfigObject.addProperty("tenantId", this.tenantIdentifier.getTenantId());

        if (shouldProtectDbConfig) {
            String[] protectedConfigs = storage.getProtectedConfigsFromSuperTokensSaaSUsers();
            for (String config : protectedConfigs) {
                if (tenantConfigObject.get("coreConfig").getAsJsonObject().has(config)) {
                    tenantConfigObject.get("coreConfig").getAsJsonObject().remove(config);
                }
            }
        }

        return tenantConfigObject;
    }
}
