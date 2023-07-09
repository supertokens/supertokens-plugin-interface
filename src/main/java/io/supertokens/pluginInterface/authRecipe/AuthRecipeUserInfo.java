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

import io.supertokens.pluginInterface.RECIPE_ID;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AuthRecipeUserInfo {

    // this is not final, cause we modify this in certain places in place (like in UsersAPI.java)
    public String id;

    public final boolean isPrimaryUser;

    public LoginMethod[] loginMethods;

    public String[] tenantIds;

    public long timeJoined;

    public AuthRecipeUserInfo(String id, boolean isPrimaryUser, LoginMethod loginMethods) {
        this.id = id;
        this.isPrimaryUser = isPrimaryUser;
        this.loginMethods = new LoginMethod[]{loginMethods};
        this.timeJoined = loginMethods.timeJoined;
        this.tenantIds = loginMethods.tenantIds;
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

        Set<String> tenantIds = new HashSet<>();
        tenantIds.addAll(Arrays.asList(loginMethod.tenantIds));
        tenantIds.addAll(Arrays.asList(this.tenantIds));
        this.tenantIds = tenantIds.toArray(new String[0]);
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
                && Arrays.equals(this.tenantIds, otherUser.tenantIds);
    }
}
