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

package io.supertokens.pluginInterface.authRecipe;

import io.supertokens.pluginInterface.RECIPE_ID;

public class LoginMethod {

    public final boolean verified;

    public final long timeJoined;

    public final String recipeUserId;

    public final RECIPE_ID recipeId;

    public final String email;

    public final String phoneNumber;

    public final ThirdParty thirdParty;

    public final String[] tenantIds;

    public transient final String passwordHash;

    public LoginMethod(String recipeUserId, long timeJoined, boolean verified, String email,
                       String passwordHash, String[] tenantIds) {
        this.verified = verified;
        this.timeJoined = timeJoined;
        this.recipeUserId = recipeUserId;
        this.recipeId = RECIPE_ID.EMAIL_PASSWORD;
        this.email = email;
        this.phoneNumber = null;
        this.thirdParty = null;
        this.tenantIds = tenantIds;
        this.passwordHash = passwordHash;
    }

    public LoginMethod(String recipeUserId, long timeJoined, boolean verified, PasswordlessInfo passwordlessInfo,
                       String[] tenantIds) {
        this.verified = verified;
        this.timeJoined = timeJoined;
        this.recipeUserId = recipeUserId;
        this.recipeId = RECIPE_ID.PASSWORDLESS;
        this.email = passwordlessInfo.email;
        this.phoneNumber = passwordlessInfo.phoneNumber;
        this.tenantIds = tenantIds;
        this.thirdParty = null;
        this.passwordHash = null;
    }

    public LoginMethod(String recipeUserId, long timeJoined, boolean verified, String email, ThirdParty thirdPartyInfo,
                       String[] tenantIds) {
        this.verified = verified;
        this.timeJoined = timeJoined;
        this.recipeUserId = recipeUserId;
        this.recipeId = RECIPE_ID.THIRD_PARTY;
        this.email = email;
        this.tenantIds = tenantIds;
        this.thirdParty = thirdPartyInfo;
        this.phoneNumber = null;
        this.passwordHash = null;
    }

    public static class PasswordlessInfo {
        String email;
        String phoneNumber;

        public PasswordlessInfo(String email, String phoneNumber) {
            if (email == null && phoneNumber == null) {
                throw new IllegalArgumentException("Both email and phoneNumber cannot be null");
            }
            this.email = email;
            this.phoneNumber = phoneNumber;
        }
    }

    public static class ThirdParty {
        public String id;
        public String userId;

        public ThirdParty(String id, String userId) {
            this.id = id;
            this.userId = userId;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof ThirdParty)) {
                return false;
            }
            ThirdParty otherThirdParty = (ThirdParty) other;
            return this.id.equals(otherThirdParty.id) && this.userId.equals(otherThirdParty.userId);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LoginMethod)) {
            return false;
        }
        LoginMethod otherLoginMethod = (LoginMethod) other;
        return this.verified == otherLoginMethod.verified && this.timeJoined == otherLoginMethod.timeJoined
                && this.recipeUserId.equals(otherLoginMethod.recipeUserId) && this.recipeId == otherLoginMethod.recipeId
                && java.util.Objects.equals(this.email, otherLoginMethod.email)
                && java.util.Objects.equals(this.phoneNumber, otherLoginMethod.phoneNumber)
                && java.util.Objects.equals(this.passwordHash, otherLoginMethod.passwordHash)
                && java.util.Objects.equals(this.thirdParty, otherLoginMethod.thirdParty)
                && java.util.Arrays.equals(this.tenantIds, otherLoginMethod.tenantIds);
    }
}
