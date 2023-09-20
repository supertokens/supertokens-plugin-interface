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

import java.util.*;

public class AuthRecipeUserInfo {

    private final String id;

    private String externalUserId = null;

    public boolean isPrimaryUser;

    public LoginMethod[] loginMethods;

    public Set<String> tenantIds;

    public long timeJoined;

    private boolean didCallSetExternalUserId = false;

    public void setExternalUserId(String externalUserId) {
        didCallSetExternalUserId = true;
        this.externalUserId = externalUserId;
        for (LoginMethod loginMethod : this.loginMethods) {
            if (loginMethod.getSupertokensUserId().equals(this.id)) {
                loginMethod.setExternalUserId(externalUserId);
            }
        }
    }

    public String getSupertokensOrExternalUserId() {
        assert (this.didCallSetExternalUserId);

        if (this.externalUserId != null) {
            return this.externalUserId;
        }
        return this.id;
    }

    public String getSupertokensUserId() {
        return this.id;
    }

    protected AuthRecipeUserInfo(String id, Boolean isPrimaryUser, LoginMethod loginMethods) {
        assert (isPrimaryUser != null);
        this.id = id;
        this.isPrimaryUser = isPrimaryUser;
        this.loginMethods = new LoginMethod[]{loginMethods};
        this.timeJoined = loginMethods.timeJoined;
        this.tenantIds = new HashSet<>();
        this.tenantIds.addAll(loginMethods.tenantIds);
    }

    public static AuthRecipeUserInfo create(String id, Boolean isPrimaryUser, LoginMethod loginMethod) {
        assert (isPrimaryUser != null);
        return new AuthRecipeUserInfo(id, isPrimaryUser, loginMethod);
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
        this.loginMethods = Arrays.stream(newLoginMethods).sorted((o1, o2) -> {
            if (o1.timeJoined < o2.timeJoined) {
                return -1;
            } else if (o1.timeJoined > o2.timeJoined) {
                return 1;
            }
            return 0;
        }).toArray(LoginMethod[]::new);
        if (timeJoined > loginMethod.timeJoined) {
            this.timeJoined = loginMethod.timeJoined;
        }

        this.tenantIds.addAll(loginMethod.tenantIds);
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
        if (!didCallSetExternalUserId) {
            throw new RuntimeException("Found a bug: Did you forget to call setExternalUserId?");
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", getSupertokensOrExternalUserId());
        jsonObject.addProperty("isPrimaryUser", this.isPrimaryUser);
        JsonArray tenantIds = new JsonArray();
        for (String tenant : this.tenantIds) {
            tenantIds.add(new JsonPrimitive(tenant));
        }
        jsonObject.add("tenantIds", tenantIds);
        jsonObject.addProperty("timeJoined", this.timeJoined);

        // now we add unique emails, phone numbers and third party across all login methods
        Set<String> emails = new HashSet<>();
        Set<String> phoneNumbers = new HashSet<>();
        Set<LoginMethod.ThirdParty> thirdParty = new HashSet<>();
        for (LoginMethod loginMethod : this.loginMethods) {
            if (loginMethod.email != null) {
                emails.add(loginMethod.email);
            }
            if (loginMethod.phoneNumber != null) {
                phoneNumbers.add(loginMethod.phoneNumber);
            }
            if (loginMethod.thirdParty != null) {
                thirdParty.add(loginMethod.thirdParty);
            }
        }
        JsonArray emailsJson = new JsonArray();
        for (String email : emails) {
            emailsJson.add(new JsonPrimitive(email));
        }
        jsonObject.add("emails", emailsJson);
        JsonArray phoneNumbersJson = new JsonArray();
        for (String phoneNumber : phoneNumbers) {
            phoneNumbersJson.add(new JsonPrimitive(phoneNumber));
        }
        jsonObject.add("phoneNumbers", phoneNumbersJson);
        JsonArray thirdPartyJson = new JsonArray();
        for (LoginMethod.ThirdParty tpInfo : thirdParty) {
            JsonObject j = new JsonObject();
            j.addProperty("id", tpInfo.id);
            j.addProperty("userId", tpInfo.userId);
            thirdPartyJson.add(j);
        }
        jsonObject.add("thirdParty", thirdPartyJson);

        // now we add login methods..
        JsonArray loginMethodsArr = new JsonArray();
        for (LoginMethod lM : this.loginMethods) {
            JsonObject lMJsonObject = new JsonObject();
            JsonArray lMTenantIds = new JsonArray();
            for (String tenant : lM.tenantIds) {
                lMTenantIds.add(new JsonPrimitive(tenant));
            }
            lMJsonObject.add("tenantIds", lMTenantIds);
            lMJsonObject.addProperty("recipeUserId", lM.getSupertokensOrExternalUserId());
            lMJsonObject.addProperty("verified", lM.verified);
            lMJsonObject.addProperty("timeJoined", lM.timeJoined);
            lMJsonObject.addProperty("recipeId", lM.recipeId.toString());
            if (lM.email != null) {
                lMJsonObject.addProperty("email", lM.email);
            }
            if (lM.phoneNumber != null) {
                lMJsonObject.addProperty("phoneNumber", lM.phoneNumber);
            }
            if (lM.thirdParty != null) {
                JsonObject thirdPartyJsonObject = new JsonObject();
                thirdPartyJsonObject.addProperty("id", lM.thirdParty.id);
                thirdPartyJsonObject.addProperty("userId", lM.thirdParty.userId);
                lMJsonObject.add("thirdParty", thirdPartyJsonObject);
            }
            loginMethodsArr.add(lMJsonObject);
        }
        jsonObject.add("loginMethods", loginMethodsArr);
        return jsonObject;
    }

    public JsonObject toJsonWithoutAccountLinking() {
        if (!didCallSetExternalUserId) {
            throw new RuntimeException("Found a bug: Did you forget to call setExternalUserId?");
        }
        // this is for older CDI versions.
        if (this.loginMethods.length != 1) {
            throw new IllegalStateException(
                    "Please use a CDI version that is greater than the one in which account linking feature was " +
                            "enabled.");
        }
        LoginMethod loginMethod = loginMethods[0];
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", loginMethod.getSupertokensOrExternalUserId());
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
