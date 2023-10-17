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

package io.supertokens.pluginInterface.mfa;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MfaFirstFactors {
    private static final Set<String> BUILT_IN_FACTORS = Set.of(
        "emailpassword",
        "thirdparty",
        "otp-email",
        "otp-phone",
        "link-email",
        "link-phone"
    );

    public final String[] builtIn;
    public final String[] custom;

    public MfaFirstFactors(String[] builtIn, String[] custom) {
        this.builtIn = builtIn == null ? new String[0] : builtIn;
        this.custom = custom == null ? new String[0] : custom;
    }

    public JsonElement toJson() {
        JsonArray result = new JsonArray();
        for (String factor : builtIn) {
            result.add(new JsonPrimitive(factor));
        }

        for (String factor : custom) {
            JsonObject customFactor = new JsonObject();
            customFactor.addProperty("type", "custom");
            customFactor.addProperty("id", factor);
            result.add(customFactor);
        }

        return result;
    }

    public static MfaFirstFactors fromJson(JsonElement input) {
        List<String> builtIn = new ArrayList<>();
        List<String> custom = new ArrayList<>();

        if (!input.isJsonArray()) throw new IllegalArgumentException("Input must be a json array");

        for (JsonElement elem : input.getAsJsonArray()) {
            if (elem.isJsonPrimitive()) {
                String factor = elem.getAsString();
                if (BUILT_IN_FACTORS.contains(factor)) {
                    builtIn.add(factor);
                } else {
                    throw new IllegalArgumentException("Factor " + factor + " is not a built-in factor");
                }
            } else if (elem.isJsonObject()) {
                JsonObject factor = elem.getAsJsonObject();
                if (factor.has("type") && factor.get("type").getAsString().equals("custom")) {
                    String factorStr = factor.get("id").getAsString();
                    if (BUILT_IN_FACTORS.contains(factorStr)) {
                        throw new IllegalArgumentException("Factor " + factorStr + " cannot be used as a custom factor");
                    }
                    custom.add(factorStr);
                } else {
                    throw new IllegalArgumentException("Invalid custom factor in the input");
                }
            }
        }

        return new MfaFirstFactors(
            builtIn.toArray(new String[0]),
            custom.toArray(new String[0])
        );
    }

    public boolean equals(Object other) {
        if (!(other instanceof MfaFirstFactors)) {
            return false;
        }

        MfaFirstFactors otherMfaFirstFactors = (MfaFirstFactors) other;

        Set<String> thisFactors = new HashSet<>();
        thisFactors.addAll(Set.of(this.builtIn));
        thisFactors.addAll(Set.of(this.custom));

        Set<String> otherFactors = new HashSet<>();
        otherFactors.addAll(Set.of(otherMfaFirstFactors.builtIn));
        otherFactors.addAll(Set.of(otherMfaFirstFactors.custom));

        return thisFactors.equals(otherFactors);
    }
}
