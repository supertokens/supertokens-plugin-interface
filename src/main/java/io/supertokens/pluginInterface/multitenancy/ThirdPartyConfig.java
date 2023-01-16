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

public class ThirdPartyConfig {
    public boolean enabled;
    public Provider[] providers;

    public ThirdPartyConfig(boolean enabled, Provider[] providers) {
        this.enabled = enabled;
        this.providers = providers;
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
    }

    public class ProviderClients {
        public String clientType;
        public String clientId;
        public String clientSecret;
        public String[] scope;
        public boolean forcePKCE;
        public JsonObject additionalConfig;
    }

    public class UserInfoMap {
        public UserInfoMapKeyValue fromIdTokenPayload;
        public UserInfoMapKeyValue fromUserInfoAPI;

        public UserInfoMap(UserInfoMapKeyValue fromIdTokenPayload, UserInfoMapKeyValue fromUserInfoAPI) {
            this.fromIdTokenPayload = fromIdTokenPayload;
            this.fromUserInfoAPI = fromUserInfoAPI;
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
    }
}
