/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

public enum ACCOUNT_INFO_TYPE {
    EMAIL("email"), PHONE_NUMBER("phone"), THIRD_PARTY("tparty");

    private final String name;

    ACCOUNT_INFO_TYPE(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static ACCOUNT_INFO_TYPE getEnumFromString(String s) {
        for (ACCOUNT_INFO_TYPE b : ACCOUNT_INFO_TYPE.values()) {
            if (b.toString().equalsIgnoreCase(s)) {
                return b;
            }
        }
        return null;
    }
}
