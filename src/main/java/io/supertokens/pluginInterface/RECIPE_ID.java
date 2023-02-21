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
 *
 */

package io.supertokens.pluginInterface;

public enum RECIPE_ID {

    EMAIL_PASSWORD("emailpassword"), THIRD_PARTY("thirdparty"), SESSION("session"),
    EMAIL_VERIFICATION("emailverification"), JWT("jwt"), PASSWORDLESS("passwordless"), USER_METADATA("usermetadata"),
    USER_ROLES("userroles"), USER_ID_MAPPING("useridmapping"), DASHBOARD("dashboard");

    private final String name;

    RECIPE_ID(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static RECIPE_ID getEnumFromString(String s) {
        for (RECIPE_ID b : RECIPE_ID.values()) {
            if (b.toString().equalsIgnoreCase(s)) {
                return b;
            }
        }
        return null;
    }

}
