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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class ThirdPartyConfig {
    public final boolean enabled;

    @Nonnull
    public final Provider[] providers;

    public ThirdPartyConfig(boolean enabled, @Nullable Provider[] providers) {
        this.enabled = enabled;
        this.providers = providers == null ? new Provider[0] : providers;
    }

    public JsonObject toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("enabled", this.enabled);
        result.add("providers", new JsonArray());

        for (Provider provider : this.providers) {
            result.getAsJsonArray("providers").add(provider.toJson());
        }

        return result;
    }

    public static boolean unorderedArrayEquals(Object[] array1, Object[] array2) {
        if (array1 == null && array2 == null) {
            return true;
        } else if (array1 == null || array2 == null) {
            return false;
        }

        List<Object> items1 = List.of(array1);
        List<Object> items2 = new ArrayList<>();
        items2.addAll(Arrays.asList(array2));

        if (items1.size() != items2.size()) return false;

        for (Object p1 : items1) {
            boolean found = false;
            for (Object p2 : items2) {
                if (p1.equals(p2)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            } else {
                items2.remove(p1);
            }
        }

        return true;
    }

    public static class Provider {

        @Nonnull
        public final String thirdPartyId;

        @Nonnull
        public final String name;

        @Nonnull
        public final ProviderClient[] clients;

        @Nullable
        public final String authorizationEndpoint;

        @Nullable
        public final JsonObject authorizationEndpointQueryParams;

        @Nullable
        public final String tokenEndpoint;

        @Nullable
        public final JsonObject tokenEndpointBodyParams;

        @Nullable
        public final String userInfoEndpoint;

        @Nullable
        public JsonObject userInfoEndpointQueryParams;

        @Nullable
        public final JsonObject userInfoEndpointHeaders;

        @Nullable
        public final String jwksURI;

        @Nullable
        public final String oidcDiscoveryEndpoint;

        @Nullable
        public final Boolean requireEmail;

        @Nonnull
        public final UserInfoMap userInfoMap;

        public Provider(@Nonnull String thirdPartyId, @Nonnull String name, @Nullable ProviderClient[] clients,
                        @Nullable String authorizationEndpoint,
                        @Nullable JsonObject authorizationEndpointQueryParams, @Nullable String tokenEndpoint,
                        @Nullable JsonObject tokenEndpointBodyParams,
                        @Nullable String userInfoEndpoint, @Nullable JsonObject userInfoEndpointQueryParams,
                        @Nullable JsonObject userInfoEndpointHeaders,
                        @Nullable String jwksURI, @Nullable String oidcDiscoveryEndpoint, @Nullable Boolean requireEmail,
                        @Nullable UserInfoMap userInfoMap) {
            this.thirdPartyId = thirdPartyId;
            this.name = name;
            this.clients = clients == null ? new ProviderClient[0] : clients;
            this.authorizationEndpoint = authorizationEndpoint;
            this.authorizationEndpointQueryParams = authorizationEndpointQueryParams;
            this.tokenEndpoint = tokenEndpoint;
            this.tokenEndpointBodyParams = tokenEndpointBodyParams;
            this.userInfoEndpoint = userInfoEndpoint;
            this.userInfoEndpointQueryParams = userInfoEndpointQueryParams;
            this.userInfoEndpointHeaders = userInfoEndpointHeaders;
            this.jwksURI = jwksURI;
            this.oidcDiscoveryEndpoint = oidcDiscoveryEndpoint;
            this.requireEmail = requireEmail;
            this.userInfoMap = userInfoMap == null ? new UserInfoMap(null, null) : userInfoMap;
        }

        public JsonObject toJson() {
            JsonObject result = new Gson().toJsonTree(this).getAsJsonObject();

            // These properties need to retain null values when serialized
            if (this.authorizationEndpointQueryParams != null) {
                result.add("authorizationEndpointQueryParams",
                        new GsonBuilder().serializeNulls().create().toJsonTree(this.authorizationEndpointQueryParams));
            } else {
                result.remove("authorizationEndpointQueryParams");
            }

            if (this.tokenEndpointBodyParams != null) {
                result.add("tokenEndpointBodyParams", new GsonBuilder().serializeNulls().create().toJsonTree(this.tokenEndpointBodyParams));
            } else {
                result.remove("tokenEndpointBodyParams");
            }

            if (this.userInfoEndpointQueryParams != null) {
                result.add("userInfoEndpointQueryParams", new GsonBuilder().serializeNulls().create().toJsonTree(this.userInfoEndpointQueryParams));
            } else {
                result.remove("userInfoEndpointQueryParams");
            }

            if (this.userInfoEndpointHeaders != null) {
                result.add("userInfoEndpointHeaders", new GsonBuilder().serializeNulls().create().toJsonTree(this.userInfoEndpointHeaders));
            } else {
                result.remove("userInfoEndpointHeaders");
            }
            return result;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (other instanceof Provider) {
                Provider otherProvider = (Provider) other;
                return Objects.equals(otherProvider.thirdPartyId, this.thirdPartyId) &&
                        Objects.equals(otherProvider.name, this.name) &&
                        unorderedArrayEquals(otherProvider.clients, this.clients) &&
                        Objects.equals(otherProvider.authorizationEndpoint, this.authorizationEndpoint) &&
                        Objects.equals(otherProvider.authorizationEndpointQueryParams,
                                this.authorizationEndpointQueryParams) &&
                        Objects.equals(otherProvider.tokenEndpoint, this.tokenEndpoint) &&
                        Objects.equals(otherProvider.tokenEndpointBodyParams, this.tokenEndpointBodyParams) &&
                        Objects.equals(otherProvider.userInfoEndpoint, this.userInfoEndpoint) &&
                        Objects.equals(otherProvider.userInfoEndpointQueryParams, this.userInfoEndpointQueryParams) &&
                        Objects.equals(otherProvider.userInfoEndpointHeaders, this.userInfoEndpointHeaders) &&
                        Objects.equals(otherProvider.jwksURI, this.jwksURI) &&
                        Objects.equals(otherProvider.oidcDiscoveryEndpoint, this.oidcDiscoveryEndpoint) &&
                        otherProvider.requireEmail == this.requireEmail &&
                        Objects.equals(otherProvider.userInfoMap, this.userInfoMap);
            }
            return false;
        }
    }

    public static class ProviderClient {

        @Nullable
        public final String clientType;

        @Nonnull
        public final String clientId;

        @Nullable
        public final String clientSecret;

        @Nullable
        public final String[] scope;

        @Nullable
        public final Boolean forcePKCE;

        @Nullable
        public final JsonObject additionalConfig;

        public ProviderClient(@Nullable String clientType, @Nonnull String clientId, @Nullable String clientSecret,
                              @Nullable String[] scope,
                              @Nullable Boolean forcePKCE, @Nullable JsonObject additionalConfig) {
            this.clientType = clientType;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.scope = scope;
            this.forcePKCE = forcePKCE;
            this.additionalConfig = additionalConfig;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof ProviderClient) {
                ProviderClient otherProviderClient = (ProviderClient) other;
                return Objects.equals(otherProviderClient.clientType, this.clientType) &&
                        otherProviderClient.clientId.equals(this.clientId) &&
                        Objects.equals(otherProviderClient.clientSecret, this.clientSecret) &&
                        unorderedArrayEquals(otherProviderClient.scope, this.scope) &&
                        otherProviderClient.forcePKCE == this.forcePKCE &&
                        Objects.equals(otherProviderClient.additionalConfig, this.additionalConfig);
            }
            return false;
        }
    }

    public static class UserInfoMap {
        @Nonnull
        public UserInfoMapKeyValue fromIdTokenPayload;

        @Nonnull
        public UserInfoMapKeyValue fromUserInfoAPI;

        public UserInfoMap(@Nullable UserInfoMapKeyValue fromIdTokenPayload,
                           @Nullable UserInfoMapKeyValue fromUserInfoAPI) {
            this.fromIdTokenPayload = fromIdTokenPayload == null ? new UserInfoMapKeyValue(null, null, null) : fromIdTokenPayload;
            this.fromUserInfoAPI = fromUserInfoAPI == null ? new UserInfoMapKeyValue(null, null, null) : fromUserInfoAPI;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof UserInfoMap) {
                UserInfoMap otherUserInfoMap = (UserInfoMap) other;
                return Objects.equals(otherUserInfoMap.fromUserInfoAPI, this.fromUserInfoAPI) &&
                        Objects.equals(otherUserInfoMap.fromIdTokenPayload, this.fromIdTokenPayload);
            }
            return false;
        }
    }

    public static class UserInfoMapKeyValue {
        @Nullable
        public String userId;

        @Nullable
        public String email;

        @Nullable
        public String emailVerified;

        public UserInfoMapKeyValue(@Nullable String userId, @Nullable String email, @Nullable String emailVerified) {
            this.userId = userId;
            this.email = email;
            this.emailVerified = emailVerified;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof UserInfoMapKeyValue) {
                UserInfoMapKeyValue otherUserInfoMapKeyValue = (UserInfoMapKeyValue) other;
                return Objects.equals(otherUserInfoMapKeyValue.userId, this.userId) &&
                        Objects.equals(otherUserInfoMapKeyValue.email, this.email) &&
                        Objects.equals(otherUserInfoMapKeyValue.emailVerified, this.emailVerified);
            }
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ThirdPartyConfig) {
            ThirdPartyConfig otherThirdPartyConfig = (ThirdPartyConfig) other;
            return otherThirdPartyConfig.enabled == this.enabled &&
                    unorderedArrayEquals(otherThirdPartyConfig.providers, this.providers);
        }
        return false;
    }

}
