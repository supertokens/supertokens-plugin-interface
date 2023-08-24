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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LoginMethod {

    public final boolean verified;

    public final long timeJoined;

    private final String recipeUserId;

    private String externalUserId;

    public final RECIPE_ID recipeId;

    public final String email;

    public final String phoneNumber;

    public final ThirdParty thirdParty;

    public final Set<String> tenantIds;

    public transient final String passwordHash;

    private boolean didCallSetExternalUserId = false;

    public LoginMethod(String recipeUserId, long timeJoined, boolean verified, String email,
                       String passwordHash, String[] tenantIds) {
        this.verified = verified;
        this.timeJoined = timeJoined;
        this.recipeUserId = recipeUserId;
        this.recipeId = RECIPE_ID.EMAIL_PASSWORD;
        this.email = email;
        this.phoneNumber = null;
        this.thirdParty = null;
        this.tenantIds = new HashSet<>();
        Collections.addAll(this.tenantIds, tenantIds);
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
        this.tenantIds = new HashSet<>();
        Collections.addAll(this.tenantIds, tenantIds);
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
        this.tenantIds = new HashSet<>();
        Collections.addAll(this.tenantIds, tenantIds);
        this.thirdParty = thirdPartyInfo;
        this.phoneNumber = null;
        this.passwordHash = null;
    }

    public void setExternalUserId(String externalUserId) {
        didCallSetExternalUserId = true;
        this.externalUserId = externalUserId;
    }

    public String getUserId() {
        // TODO enable this while implementing external user id for login methods
        // assert (this.didCallSetExternalUserId);

        if (this.externalUserId != null) {
            return this.externalUserId;
        }
        return this.recipeUserId;
    }

    // This function should never be called in the API layer unless there is a strong reason to do so
    public String getRecipeUserIdNotToBeReturnedFromAPI() {
        return this.recipeUserId;
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

        @Override
        public int hashCode() {
            return (id + "|" + userId).hashCode();
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
                && this.tenantIds.equals(otherLoginMethod.tenantIds);
    }

    @Override
    public int hashCode() {
        // combine hash codes of all fields
        // We multiply with 31 because it's a prime number.
        int result = 1;
        result = 31 * result + (verified ? 1 : 0);
        result = 31 * result + (int) (timeJoined ^ (timeJoined >>> 32));
        result = 31 * result + recipeUserId.hashCode();
        result = 31 * result + recipeId.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + tenantIds.hashCode();
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (thirdParty != null ? thirdParty.hashCode() : 0);
        return result;
    }
}
