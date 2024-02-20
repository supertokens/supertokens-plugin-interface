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

package io.supertokens.pluginInterface.bulkimport;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BulkImportUser {
    public String id;
    public String externalUserId;
    public JsonObject userMetadata;
    public List<String> userRoles;
    public List<TotpDevice> totpDevices;
    public List<LoginMethod> loginMethods;

    public BulkImportUser(String id, String externalUserId, JsonObject userMetadata, List<String> userRoles, List<TotpDevice> totpDevices, List<LoginMethod> loginMethods) {
        this.id = id;
        this.externalUserId = externalUserId;
        this.userMetadata = userMetadata;
        this.userRoles = userRoles;
        this.totpDevices = totpDevices;
        this.loginMethods = loginMethods;
    }

   public String toString() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();

        json.addProperty("externalUserId", externalUserId);
        json.add("userMetadata", userMetadata);
        json.add("roles", gson.toJsonTree(userRoles));
        json.add("totp", gson.toJsonTree(totpDevices));
        json.add("loginMethods", gson.toJsonTree(loginMethods));

        return json.toString();
    }

    public static class TotpDevice {
        public String secretKey;
        public Number period;
        public Number skew;
        public String deviceName;

        public TotpDevice(String secretKey, Number period, Number skew, String deviceName) {
            this.secretKey = secretKey;
            this.period = period;
            this.skew = skew;
            this.deviceName = deviceName;
        }
    }

    public static class LoginMethod {
        public String tenantId;
        public Boolean isVerified;
        public Boolean isPrimary;
        public long timeJoinedInMSSinceEpoch;
        public String recipeId;

        public EmailPasswordLoginMethod emailPasswordLoginMethod;
        public ThirdPartyLoginMethod thirdPartyLoginMethod;
        public PasswordlessLoginMethod passwordlessLoginMethod;

        public LoginMethod(String tenantId, String recipeId, Boolean isVerified, Boolean isPrimary, long timeJoinedInMSSinceEpoch, EmailPasswordLoginMethod emailPasswordLoginMethod, ThirdPartyLoginMethod thirdPartyLoginMethod, PasswordlessLoginMethod passwordlessLoginMethod) {
            this.tenantId = tenantId;
            this.recipeId = recipeId;
            this.isVerified = isVerified;
            this.isPrimary = isPrimary;
            this.timeJoinedInMSSinceEpoch = timeJoinedInMSSinceEpoch;
            this.emailPasswordLoginMethod = emailPasswordLoginMethod;
            this.thirdPartyLoginMethod = thirdPartyLoginMethod;
            this.passwordlessLoginMethod = passwordlessLoginMethod;
        }

        public static class EmailPasswordLoginMethod {
            public String email;
            public String passwordHash;
            public String hashingAlgorithm;

            public EmailPasswordLoginMethod(String email, String passwordHash, String hashingAlgorithm) {
                this.email = email;
                this.passwordHash = passwordHash;
                this.hashingAlgorithm = hashingAlgorithm;
            }
        }

        public static class ThirdPartyLoginMethod {
            public String email;
            public String thirdPartyId;
            public String thirdPartyUserId;

            public ThirdPartyLoginMethod(String email, String thirdPartyId, String thirdPartyUserId) {
                this.email = email;
                this.thirdPartyId = thirdPartyId;
                this.thirdPartyUserId = thirdPartyUserId;
            }
        }

        public static class PasswordlessLoginMethod {
            public String email;
            public String phoneNumber;

            public PasswordlessLoginMethod(String email, String phoneNumber) {
                this.email = email;
                this.phoneNumber = phoneNumber;
            }
        }
    }
}
