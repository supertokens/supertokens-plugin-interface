/*
 *    Copyright (c) 2021, VRAI Labs and/or its affiliates. All rights reserved.
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.supertokens.pluginInterface.RECIPE_ID;

import java.util.Arrays;
import java.util.Set;

public class AuthRecipeUserInfo {

    public final String id;

    private String externalUserId = null;

    public boolean isPrimaryUser;

    public LoginMethod[] loginMethods;

    public Set<String> tenantIds;

    public long timeJoined;

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
        for (LoginMethod loginMethod : this.loginMethods) {
            if (loginMethod.recipeUserId.equals(this.id)) {
                loginMethod.externalUserId = externalUserId;
            }
        }
    }

    protected AuthRecipeUserInfo(String id, Boolean isPrimaryUser, LoginMethod loginMethods) {
        assert (isPrimaryUser != null);
        this.id = id;
        this.isPrimaryUser = isPrimaryUser;
        this.loginMethods = new LoginMethod[]{loginMethods};
        this.timeJoined = loginMethods.timeJoined;
        this.tenantIds = loginMethods.tenantIds;
    }

    public static AuthRecipeUserInfo create(String id, Boolean isPrimaryUser, LoginMethod loginMethod) {
        assert (isPrimaryUser != null);
        if (loginMethod.recipeId == RECIPE_ID.EMAIL_PASSWORD) {
            return new io.supertokens.pluginInterface.emailpassword.UserInfo(id, isPrimaryUser, loginMethod);
        } else if (loginMethod.recipeId == RECIPE_ID.PASSWORDLESS) {
            return new io.supertokens.pluginInterface.passwordless.UserInfo(id, isPrimaryUser, loginMethod);
        } else if (loginMethod.recipeId == RECIPE_ID.THIRD_PARTY) {
            return new io.supertokens.pluginInterface.thirdparty.UserInfo(id, isPrimaryUser, loginMethod);
        } else {
            throw new UnsupportedOperationException("Please search for bugs");
        }
    }

    public void addLoginMethod(LoginMethod loginMethod) {
        for (LoginMethod method : this.loginMethods) {
            if (method.equals(loginMethod)) {
                return;
            }
        }
        LoginMethod[] newLoginMethods = new LoginMethod[this.loginMethods.length + 1];
        System.arraycopy(this.loginMethods, 0, newLoginMethods, 0, this.loginMethods.length);
        newLoginMethods[this.loginMethods.length] = loginMethod;
        this.loginMethods = newLoginMethods;
        if (timeJoined > loginMethod.timeJoined) {
            this.timeJoined = loginMethod.timeJoined;
        }

        this.tenantIds.addAll(loginMethod.tenantIds);
    }

    public RECIPE_ID getRecipeId() {
        throw new UnsupportedOperationException("Please search for bugs");
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AuthRecipeUserInfo)) {
            return false;
        }
        AuthRecipeUserInfo otherUser = (AuthRecipeUserInfo) other;
        return this.id.equals(otherUser.id) && this.isPrimaryUser == otherUser.isPrimaryUser
                && this.timeJoined == otherUser.timeJoined && Arrays.equals(this.loginMethods, otherUser.loginMethods)
                && this.tenantIds.equals(otherUser.tenantIds);
    }

    @Override
    public int hashCode() {
        // combine hash codes of all fields
        // We multiply with 31 because it's a prime number.
        int hashCode = this.id.hashCode();
        hashCode = 31 * hashCode + Boolean.hashCode(this.isPrimaryUser);
        hashCode = 31 * hashCode + Long.hashCode(this.timeJoined);
        hashCode = 31 * hashCode + Arrays.hashCode(this.loginMethods);
        hashCode = 31 * hashCode + this.tenantIds.hashCode();
        return hashCode;
    }

    public JsonObject toJson() {
        // TODO: also take into account external user ID
        throw new RuntimeException("TODO: Needs to be implemented");
    }

    public JsonObject toJsonWithoutAccountLinking() {
        // this is for older CDI versions.
        if (this.loginMethods.length != 1) {
            throw new IllegalStateException(
                    "Please use a CDI version that is greater than the one in which account linking feature was " +
                            "enabled.");
        }
        LoginMethod loginMethod = loginMethods[0];
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",
                loginMethod.externalUserId == null ? loginMethod.recipeUserId : loginMethod.externalUserId);
        jsonObject.addProperty("timeJoined", loginMethod.timeJoined);
        JsonArray tenantIds = new JsonArray();
        for (String tenant : loginMethod.tenantIds) {
            tenantIds.add(new JsonPrimitive(tenant));
        }
        jsonObject.add("tenantIds", tenantIds);
        if (loginMethod.recipeId == RECIPE_ID.EMAIL_PASSWORD) {
            jsonObject.addProperty("email", loginMethod.email);
        } else if (loginMethod.recipeId == RECIPE_ID.THIRD_PARTY) {
            jsonObject.addProperty("email", loginMethod.email);
            JsonObject thirdPartyJson = new JsonObject();
            assert loginMethod.thirdParty != null;
            thirdPartyJson.addProperty("id", loginMethod.thirdParty.id);
            thirdPartyJson.addProperty("userId", loginMethod.thirdParty.userId);
            jsonObject.add("thirdParty", thirdPartyJson);
        } else if (loginMethod.recipeId == RECIPE_ID.PASSWORDLESS) {
            if (loginMethod.email != null) {
                jsonObject.addProperty("email", loginMethod.email);
            }
            if (loginMethod.phoneNumber != null) {
                jsonObject.addProperty("phoneNumber", loginMethod.phoneNumber);
            }
        } else {
            throw new UnsupportedOperationException("Please search for bugs");
        }

        return jsonObject;
    }
}
