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

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class ThirdPartyConfig {
    public final boolean enabled;

    @Nonnull
    public final Provider[] providers;

    public ThirdPartyConfig(boolean enabled, @Nullable Provider[] providers) {
        this.enabled = enabled;
        this.providers = providers == null ? new Provider[0] : providers;
    }

    public static class Provider {

        @Nonnull
        public final String thirdPartyId;

        @Nonnull
        public final String name;

        @Nullable
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

        public final boolean requireEmail;

        @Nullable
        public final UserInfoMap userInfoMap;

        public Provider(@Nonnull String thirdPartyId, @Nonnull String name, @Nullable ProviderClient[] clients,
                        @Nullable String authorizationEndpoint,
                        @Nullable JsonObject authorizationEndpointQueryParams, @Nullable String tokenEndpoint,
                        @Nullable JsonObject tokenEndpointBodyParams,
                        @Nullable String userInfoEndpoint, @Nullable JsonObject userInfoEndpointQueryParams,
                        @Nullable JsonObject userInfoEndpointHeaders,
                        @Nullable String jwksURI, @Nullable String oidcDiscoveryEndpoint, boolean requireEmail,
                        @Nullable UserInfoMap userInfoMap) {
            this.thirdPartyId = thirdPartyId;
            this.name = name;
            this.clients = clients;
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
            this.userInfoMap = userInfoMap;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Provider) {
                Provider otherProvider = (Provider) other;
                return otherProvider.thirdPartyId.equals(this.thirdPartyId) &&
                        otherProvider.name.equals(this.name) &&
                        Arrays.equals(otherProvider.clients, this.clients) &&
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

        public final boolean forcePKCE;

        @Nullable
        public final JsonObject additionalConfig;

        public ProviderClient(@Nullable String clientType, @Nonnull String clientId, @Nullable String clientSecret,
                              @Nullable String[] scope,
                              boolean forcePKCE, @Nullable JsonObject additionalConfig) {
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
                        Arrays.equals(otherProviderClient.scope, this.scope) &&
                        otherProviderClient.forcePKCE == this.forcePKCE &&
                        Objects.equals(otherProviderClient.additionalConfig, this.additionalConfig);
            }
            return false;
        }
    }

    public static class UserInfoMap {
        @Nullable
        public UserInfoMapKeyValue fromIdTokenPayload;

        @Nullable
        public UserInfoMapKeyValue fromUserInfoAPI;

        public UserInfoMap(@Nullable UserInfoMapKeyValue fromIdTokenPayload,
                           @Nullable UserInfoMapKeyValue fromUserInfoAPI) {
            this.fromIdTokenPayload = fromIdTokenPayload;
            this.fromUserInfoAPI = fromUserInfoAPI;
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
        @Nonnull
        public String userId;

        @Nonnull
        public String email;

        @Nonnull
        public String emailVerified;

        public UserInfoMapKeyValue(@Nonnull String userId, @Nonnull String email, @Nonnull String emailVerified) {
            this.userId = userId;
            this.email = email;
            this.emailVerified = emailVerified;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof UserInfoMapKeyValue) {
                UserInfoMapKeyValue otherUserInfoMapKeyValue = (UserInfoMapKeyValue) other;
                return otherUserInfoMapKeyValue.userId.equals(this.userId) &&
                        otherUserInfoMapKeyValue.email.equals(this.email) &&
                        otherUserInfoMapKeyValue.emailVerified.equals(this.emailVerified);
            }
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ThirdPartyConfig) {
            ThirdPartyConfig otherThirdPartyConfig = (ThirdPartyConfig) other;
            return otherThirdPartyConfig.enabled == this.enabled &&
                    Arrays.equals(otherThirdPartyConfig.providers, this.providers);
        }
        return false;
    }

}
