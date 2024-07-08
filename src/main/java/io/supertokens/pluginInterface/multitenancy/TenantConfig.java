/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
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
import io.supertokens.pluginInterface.utils.Utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    @SerializedName("firstFactors")
    public final String[] firstFactors;

    @Nullable
    @SerializedName("requiredSecondaryFactors")
    public final String[] requiredSecondaryFactors;

    @Nonnull
    public final JsonObject coreConfig;

    public TenantConfig(@Nonnull TenantIdentifier tenantIdentifier, @Nonnull EmailPasswordConfig emailPasswordConfig,
                        @Nonnull ThirdPartyConfig thirdPartyConfig,
                        @Nonnull PasswordlessConfig passwordlessConfig,
                        @Nullable String[] firstFactors,
                        @Nullable String[] requiredSecondaryFactors,
                        @Nullable JsonObject coreConfig) {
        this.tenantIdentifier = tenantIdentifier;
        this.coreConfig = coreConfig == null ? new JsonObject() : coreConfig;
        this.emailPasswordConfig = emailPasswordConfig;
        this.passwordlessConfig = passwordlessConfig;
        this.thirdPartyConfig = thirdPartyConfig;
        this.firstFactors = firstFactors;
        this.requiredSecondaryFactors =
                requiredSecondaryFactors == null || requiredSecondaryFactors.length == 0 ? null :
                        requiredSecondaryFactors;
    }

    public TenantConfig(TenantConfig other) {
        // copy constructor, that does a deep copy
        Gson gson = new Gson();
        this.tenantIdentifier = new TenantIdentifier(other.tenantIdentifier.getConnectionUriDomain(),
                other.tenantIdentifier.getAppId(), other.tenantIdentifier.getTenantId());
        this.coreConfig = gson.fromJson(other.coreConfig.toString(), JsonObject.class);
        this.emailPasswordConfig = new EmailPasswordConfig(other.emailPasswordConfig.enabled);
        this.passwordlessConfig = new PasswordlessConfig(other.passwordlessConfig.enabled);
        this.thirdPartyConfig = new ThirdPartyConfig(
                other.thirdPartyConfig.enabled,
                other.thirdPartyConfig.providers != null ? other.thirdPartyConfig.providers.clone() : null);
        this.firstFactors = other.firstFactors == null ? null : other.firstFactors.clone();
        this.requiredSecondaryFactors =
                other.requiredSecondaryFactors == null ? null : other.requiredSecondaryFactors.clone();
    }

    public boolean deepEquals(TenantConfig other) {
        if (other == null) {
            return false;
        }
        return this.tenantIdentifier.equals(other.tenantIdentifier) &&
                this.emailPasswordConfig.equals(other.emailPasswordConfig) &&
                this.passwordlessConfig.equals(other.passwordlessConfig) &&
                this.thirdPartyConfig.equals(other.thirdPartyConfig) &&
                Utils.unorderedArrayEquals(this.firstFactors, other.firstFactors) && // order is not important
                Utils.unorderedArrayEquals(this.requiredSecondaryFactors, other.requiredSecondaryFactors) &&
                // order is not important
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

    private void protectConfigs(JsonObject tenantConfigObject, Storage storage, String[] protectedCoreConfigs) {
        String[] protectedConfigs = storage.getProtectedConfigsFromSuperTokensSaaSUsers();
        for (String config : protectedConfigs) {
            if (tenantConfigObject.get("coreConfig").getAsJsonObject().has(config)) {
                tenantConfigObject.get("coreConfig").getAsJsonObject().remove(config);
            }
        }

        for (String config : protectedCoreConfigs) {
            if (tenantConfigObject.get("coreConfig").getAsJsonObject().has(config)) {
                tenantConfigObject.get("coreConfig").getAsJsonObject().remove(config);
            }
        }
    }

    public JsonObject toJsonLesserThanOrEqualTo4_0(boolean shouldProtectDbConfig, Storage storage, String[] protectedCoreConfigs) {
        JsonObject result = new JsonObject();
        result.addProperty("tenantId", this.tenantIdentifier.getTenantId());
        result.add("emailPassword", this.emailPasswordConfig.toJsonLesserThanOrEqualTo4_0(this.firstFactors));
        result.add("thirdParty", this.thirdPartyConfig.toJsonLesserThanOrEqualTo4_0(this.firstFactors));
        result.add("passwordless", this.passwordlessConfig.toJsonLesserThanOrEqualTo4_0(this.firstFactors));
        result.add("coreConfig", this.coreConfig);

        if (shouldProtectDbConfig) {
            this.protectConfigs(result, storage, protectedCoreConfigs);
        }

        return result;
    }

    public JsonObject toJson5_0(boolean shouldProtectDbConfig, Storage storage, String[] protectedCoreConfigs) {
        JsonObject result = new JsonObject();
        result.addProperty("tenantId", this.tenantIdentifier.getTenantId());
        result.add("emailPassword", this.emailPasswordConfig.toJson5_0(this.firstFactors));
        result.add("thirdParty", this.thirdPartyConfig.toJson5_0(this.firstFactors));
        result.add("passwordless", this.passwordlessConfig.toJson5_0(this.firstFactors));

        if (this.firstFactors != null && this.firstFactors.length > 0) {
            result.add("firstFactors", new Gson().toJsonTree(this.firstFactors));
        }
        if (this.requiredSecondaryFactors != null) {
            result.add("requiredSecondaryFactors", new Gson().toJsonTree(this.requiredSecondaryFactors));
        }
        result.add("coreConfig", this.coreConfig);

        if (shouldProtectDbConfig) {
            this.protectConfigs(result, storage, protectedCoreConfigs);
        }

        return result;
    }

    public JsonObject toJson_v2_5_1(boolean shouldProtectDbConfig, Storage storage, String[] protectedCoreConfigs) {
        JsonObject result = new JsonObject();
        result.addProperty("tenantId", this.tenantIdentifier.getTenantId());
        result.add("thirdParty", this.thirdPartyConfig.toJson_v2_5_1());

        List<String> firstFactors = this.firstFactors == null ? null : new ArrayList<>(List.of(this.firstFactors));

        if (firstFactors == null) {
            if (!this.emailPasswordConfig.enabled ||
                    !this.thirdPartyConfig.enabled || !this.passwordlessConfig.enabled) {
                firstFactors = new ArrayList<>();
                if (this.emailPasswordConfig.enabled) {
                    firstFactors.add("emailpassword");
                }
                if (this.thirdPartyConfig.enabled) {
                    firstFactors.add("thirdparty");
                }
                if (this.passwordlessConfig.enabled) {
                    firstFactors.add("otp-email");
                    firstFactors.add("otp-phone");
                    firstFactors.add("link-email");
                    firstFactors.add("link-phone");
                }
            }
        }

        if (firstFactors != null) {
            result.add("firstFactors", new Gson().toJsonTree(firstFactors));
        }

        if (this.requiredSecondaryFactors != null) {
            result.add("requiredSecondaryFactors", new Gson().toJsonTree(this.requiredSecondaryFactors));
        }

        result.add("coreConfig", this.coreConfig);
        if (shouldProtectDbConfig) {
            this.protectConfigs(result, storage, protectedCoreConfigs);
        }

        return result;
    }
}
