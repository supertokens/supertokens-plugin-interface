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

import io.supertokens.pluginInterface.bulkimport.BulkImportStorage.BulkImportUserStatus;

public class BulkImportUser {
    public String id;
    public String externalUserId;
    public JsonObject userMetadata;
    public List<String> userRoles;
    public List<TotpDevice> totpDevices;
    public List<LoginMethod> loginMethods;

    // Following fields come from the DB Record.
    public BulkImportUserStatus status;
    public Long createdAt;
    public Long updatedAt;

    public BulkImportUser(String id, String externalUserId, JsonObject userMetadata, List<String> userRoles,
            List<TotpDevice> totpDevices, List<LoginMethod> loginMethods) {
        this.id = id;
        this.externalUserId = externalUserId;
        this.userMetadata = userMetadata;
        this.userRoles = userRoles;
        this.totpDevices = totpDevices;
        this.loginMethods = loginMethods;
    }

    public static BulkImportUser fromJson(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, BulkImportUser.class);
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    // This method returns a JSON object string representation, excluding 'status', 'createdAt', and 'updatedAt'. Useful for test comparisons.
    public String toRawData() {
        JsonObject jsonObject = new Gson().fromJson(this.toString(), JsonObject.class);
        jsonObject.remove("status");
        jsonObject.remove("createdAt");
        jsonObject.remove("updatedAt");
        return jsonObject.toString();
    }
    
    public JsonObject toJsonObject() {
        return new Gson().fromJson(this.toString(), JsonObject.class);
    }

    public static class TotpDevice {
        public String secretKey;
        public int period;
        public int skew;
        public String deviceName;

        public TotpDevice(String secretKey, int period, int skew, String deviceName) {
            this.secretKey = secretKey;
            this.period = period;
            this.skew = skew;
            this.deviceName = deviceName;
        }
    }

    public static class LoginMethod {
        public String tenantId;
        public boolean isVerified;
        public boolean isPrimary;
        public long timeJoinedInMSSinceEpoch;
        public String recipeId;
        public String email;
        public String passwordHash;
        public String hashingAlgorithm;
        public String thirdPartyId;
        public String thirdPartyUserId;
        public String phoneNumber;

        public LoginMethod(String tenantId, String recipeId, boolean isVerified, boolean isPrimary,
                long timeJoinedInMSSinceEpoch, String email, String passwordHash, String hashingAlgorithm,
                String thirdPartyId, String thirdPartyUserId, String phoneNumber) {
            this.tenantId = tenantId;
            this.recipeId = recipeId;
            this.isVerified = isVerified;
            this.isPrimary = isPrimary;
            this.timeJoinedInMSSinceEpoch = timeJoinedInMSSinceEpoch;
            this.email = email;
            this.passwordHash = passwordHash;
            this.hashingAlgorithm = hashingAlgorithm;
            this.thirdPartyId = thirdPartyId;
            this.thirdPartyUserId = thirdPartyUserId;
            this.phoneNumber = phoneNumber;
        }
    }
}
