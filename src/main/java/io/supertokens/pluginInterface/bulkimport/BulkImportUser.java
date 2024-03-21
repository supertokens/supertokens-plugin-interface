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

import io.supertokens.pluginInterface.bulkimport.BulkImportStorage.BULK_IMPORT_USER_STATUS;

public class BulkImportUser {
    public String id;
    public String externalUserId;
    public JsonObject userMetadata;
    public List<UserRole> userRoles;
    public List<TotpDevice> totpDevices;
    public List<LoginMethod> loginMethods;

    // Following fields come from the DB Record.
    public BULK_IMPORT_USER_STATUS status;
    public String errorMessage;
    public Long createdAt;
    public Long updatedAt;

    public BulkImportUser(String id, String externalUserId, JsonObject userMetadata, List<UserRole> userRoles,
            List<TotpDevice> totpDevices, List<LoginMethod> loginMethods) {
        this.id = id;
        this.externalUserId = externalUserId;
        this.userMetadata = userMetadata;
        this.userRoles = userRoles;
        this.totpDevices = totpDevices;
        this.loginMethods = loginMethods;
    }

    public static BulkImportUser forTesting_fromJson(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject, BulkImportUser.class);
    }

    // This method returns a JSON object string representation, excluding 'status', 'createdAt', and 'updatedAt'.
    // It is used for inserting the user into the database or during testing.
    public String toRawDataForDbStorage() {
        JsonObject jsonObject = new Gson().fromJson(new Gson().toJson(this), JsonObject.class);
        jsonObject.remove("status");
        jsonObject.remove("createdAt");
        jsonObject.remove("updatedAt");
        return jsonObject.toString();
    }

    public static BulkImportUser fromRawDataFromDbStorage(String id, String rawData, BULK_IMPORT_USER_STATUS status, String errorMessage, long createdAt, long updatedAt) {
        BulkImportUser user = new Gson().fromJson(rawData, BulkImportUser.class);
        user.id = id;
        user.status = status;
        user.errorMessage = errorMessage;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }
    
    public JsonObject toJsonObject() {
        return new Gson().fromJson(new Gson().toJson(this), JsonObject.class);
    }

    public static class UserRole {
        public String role;
        public List<String> tenantIds;

        public UserRole(String role, List<String> tenantIds) {
            this.role = role;
            this.tenantIds = tenantIds;
        }
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
        public List<String> tenantIds;
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
        public String superTokensOrExternalUserId;

        public LoginMethod(List<String> tenantIds, String recipeId, boolean isVerified, boolean isPrimary,
                long timeJoinedInMSSinceEpoch, String email, String passwordHash, String hashingAlgorithm,
                String thirdPartyId, String thirdPartyUserId, String phoneNumber) {
            this.tenantIds = tenantIds;
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
