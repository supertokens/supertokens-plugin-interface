/*
 *    Copyright (c) 2020, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.passwordless;

import javax.annotation.Nullable;

import io.supertokens.pluginInterface.RECIPE_ID;
import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;

public class UserInfo extends AuthRecipeUserInfo {
    public final String email;
    public final String phoneNumber;

    public UserInfo(String id, @Nullable String email, @Nullable String phoneNumber, long timeJoined, String[] tenantIds) {
        super(id, timeJoined, tenantIds);

        if (email == null && phoneNumber == null) {
            throw new IllegalArgumentException("Both email and phoneNumber cannot be null");
        }

        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public RECIPE_ID getRecipeId() {
        return RECIPE_ID.PASSWORDLESS;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UserInfo) {
            UserInfo otherUserInfo = (UserInfo) other;
            return ((otherUserInfo.email == null && this.email == null) || otherUserInfo.email.equals(this.email))
                    && ((otherUserInfo.phoneNumber == null && this.phoneNumber == null)
                            || otherUserInfo.phoneNumber.equals(this.phoneNumber))
                    && otherUserInfo.id.equals(this.id) && otherUserInfo.timeJoined == this.timeJoined;
        }
        return false;
    }
}
