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

import java.util.Arrays;
import java.util.Objects;

public class ThirdPartyConfig {
    public boolean enabled;
    private Provider[] providers;

    public ThirdPartyConfig(boolean enabled, Provider[] providers) {
        this.enabled = enabled;
        this.providers = providers;
    }

    public Provider[] getProviders() {
        if (this.providers == null) {
            return new Provider[0];
        }
        return providers;
    }

    public class Provider {
        public String thirdPartyId;
        public String name;
        public ProviderClients[] clients;
        public String authorizationEndpoint;
        public JsonObject authorizationEndpointQueryParams;
        public String tokenEndpoint;
        public JsonObject tokenEndpointBodyParams;
        public String userInfoEndpoint;
        public JsonObject userInfoEndpointQueryParams;
        public JsonObject userInfoEndpointHeaders;
        public String jwksURI;
        public String oidcDiscoveryEndpoint;
        public boolean requireEmail;
        public UserInfoMap userInfoMap;

        @Override
        public boolean equals(Object other) {
            if (other instanceof Provider) {
                Provider otherProvider = (Provider) other;
                return otherProvider.thirdPartyId.equals(this.thirdPartyId) &&
                        otherProvider.name.equals(this.name) &&
                        Arrays.equals(otherProvider.clients, this.clients) &&
                        otherProvider.authorizationEndpoint.equals(this.authorizationEndpoint) &&
                        otherProvider.authorizationEndpointQueryParams.equals(this.authorizationEndpointQueryParams) &&
                        otherProvider.tokenEndpoint.equals(this.tokenEndpoint) &&
                        otherProvider.tokenEndpointBodyParams.equals(this.tokenEndpointBodyParams) &&
                        otherProvider.userInfoEndpoint.equals(this.userInfoEndpoint) &&
                        otherProvider.userInfoEndpointQueryParams.equals(this.userInfoEndpointQueryParams) &&
                        otherProvider.userInfoEndpointHeaders.equals(this.userInfoEndpointHeaders) &&
                        otherProvider.jwksURI.equals(this.jwksURI) &&
                        otherProvider.oidcDiscoveryEndpoint.equals(this.oidcDiscoveryEndpoint) &&
                        otherProvider.requireEmail == this.requireEmail &&
                        otherProvider.userInfoMap.equals(this.userInfoMap);
            }
            return false;
        }
    }

    public class ProviderClients {
        public String clientType;
        public String clientId;
        public String clientSecret;
        public String[] scope;
        public boolean forcePKCE;
        public JsonObject additionalConfig;

        @Override
        public boolean equals(Object other) {
            if (other instanceof ProviderClients) {
                ProviderClients otherProviderClients = (ProviderClients) other;
                return otherProviderClients.clientType.equals(this.clientType) &&
                        otherProviderClients.clientId.equals(this.clientId) &&
                        otherProviderClients.clientSecret.equals(this.clientSecret) &&
                        Arrays.equals(otherProviderClients.scope, this.scope) &&
                        otherProviderClients.forcePKCE == this.forcePKCE &&
                        otherProviderClients.additionalConfig.equals(this.additionalConfig);
            }
            return false;
        }
    }

    public class UserInfoMap {
        public UserInfoMapKeyValue fromIdTokenPayload;
        public UserInfoMapKeyValue fromUserInfoAPI;

        public UserInfoMap(UserInfoMapKeyValue fromIdTokenPayload, UserInfoMapKeyValue fromUserInfoAPI) {
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

    public class UserInfoMapKeyValue {
        public String userId;
        public String email;
        public String emailVerified;

        public UserInfoMapKeyValue(String userId, String email, String emailVerified) {
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
                    Arrays.equals(otherThirdPartyConfig.getProviders(), this.getProviders());
        }
        return false;
    }

}
