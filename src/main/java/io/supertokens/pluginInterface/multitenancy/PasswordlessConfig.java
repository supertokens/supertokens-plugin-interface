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

package io.supertokens.pluginInterface.multitenancy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class PasswordlessConfig {
    public boolean enabled;

    public PasswordlessConfig(boolean enabled) {
        this.enabled = enabled;
    }

    public static PasswordlessConfig fromJSONString(String json) {
        JsonParser jp = new JsonParser();
        JsonObject obj = jp.parse(json).getAsJsonObject();
        return new PasswordlessConfig(obj.get("enabled").getAsBoolean());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PasswordlessConfig) {
            PasswordlessConfig otherPasswordlessConfig = (PasswordlessConfig) other;
            return otherPasswordlessConfig.enabled == this.enabled;
        }
        return false;
    }

    public JsonObject toJSON() {
        JsonObject result = new JsonObject();
        result.add("enabled", new JsonPrimitive(enabled));
        return result;
    }
}
