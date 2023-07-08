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

package io.supertokens.pluginInterface.emailpassword;

import io.supertokens.pluginInterface.RECIPE_ID;
import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;
import io.supertokens.pluginInterface.authRecipe.LoginMethod;

public class UserInfo extends AuthRecipeUserInfo {

    public final String email;

    // using transient, we tell Gson not to include this when creating a JSON
    public transient final String passwordHash;

    public UserInfo(String id, boolean verified, LoginMethod loginMethod) {
        super(id, verified, loginMethod);
        this.email = loginMethod.email;
        this.passwordHash = loginMethod.passwordHash;
    }

    @Override
    public RECIPE_ID getRecipeId() {
        return RECIPE_ID.EMAIL_PASSWORD;
    }

}
