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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.bulkimport.BulkImportStorage.BULK_IMPORT_USER_STATUS;

import java.util.List;

public class BulkImportUser {
    public String id;
    public String externalUserId;
    public JsonObject userMetadata;
    public List<UserRole> userRoles;
    public List<TotpDevice> totpDevices;
    public List<LoginMethod> loginMethods;

    // Following fields come from the DB Record.
    public BULK_IMPORT_USER_STATUS status;
    public String primaryUserId;
    public String errorMessage;
    public Long createdAt;
    public Long updatedAt;

    private volatile static Gson gson = new Gson();

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
        return gson.fromJson(jsonObject, BulkImportUser.class);
    }

    // The bulk_import_users table stores users to be imported via a Cron Job.
    // It has a `raw_data` column containing user data in JSON format.

    // The BulkImportUser class represents this `raw_data`, including additional fields like `status`, `createdAt`, and `updatedAt`.
    // First, we validate all fields of `raw_data` using the BulkImportUser class, then store this data in the bulk_import_users table.
    // This function retrieves the `raw_data` after removing the additional fields.
    public String toRawDataForDbStorage() {
        JsonObject jsonObject = gson.fromJson(new Gson().toJson(this), JsonObject.class);
        jsonObject.remove("status");
        jsonObject.remove("createdAt");
        jsonObject.remove("updatedAt");
        return jsonObject.toString();
    }

    // The bulk_import_users table contains a `raw_data` column with user data in JSON format, along with other columns such as `id`, `status`, `primary_user_id`, and `error_msg` etc.

    // When creating an instance of the BulkImportUser class, the extra fields must be passed separately as they are not part of the `raw_data`. 
    // This function creates a BulkImportUser instance from a stored bulk_import_user entry.
    public static BulkImportUser fromRawDataFromDbStorage(String id, String rawData, BULK_IMPORT_USER_STATUS status, String primaryUserId, String errorMessage, long createdAt, long updatedAt) {
        BulkImportUser user = gson.fromJson(rawData, BulkImportUser.class);
        user.id = id;
        user.status = status;
        user.primaryUserId = primaryUserId;
        user.errorMessage = errorMessage;
        user.createdAt = createdAt;
        user.updatedAt = updatedAt;
        return user;
    }
    
    public JsonObject toJsonObject() {
        return gson.fromJson(gson.toJson(this), JsonObject.class);
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
        public String plainTextPassword;
        public String thirdPartyId;
        public String thirdPartyUserId;
        public String phoneNumber;
        public String superTokensUserId;
        public String externalUserId;

        public String getSuperTokenOrExternalUserId() {
            return this.externalUserId != null ? this.externalUserId : this.superTokensUserId;
        }

        public LoginMethod(List<String> tenantIds, String recipeId, boolean isVerified, boolean isPrimary,
                long timeJoinedInMSSinceEpoch, String email, String passwordHash, String hashingAlgorithm, String plainTextPassword,
                String thirdPartyId, String thirdPartyUserId, String phoneNumber) {
            this.tenantIds = tenantIds;
            this.recipeId = recipeId;
            this.isVerified = isVerified;
            this.isPrimary = isPrimary;
            this.timeJoinedInMSSinceEpoch = timeJoinedInMSSinceEpoch;
            this.email = email;
            this.passwordHash = passwordHash;
            this.hashingAlgorithm = hashingAlgorithm;
            this.plainTextPassword = plainTextPassword;
            this.thirdPartyId = thirdPartyId;
            this.thirdPartyUserId = thirdPartyUserId;
            this.phoneNumber = phoneNumber;
        }
    }
}
