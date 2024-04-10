/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
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

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConfigFieldInfo {
    @Nonnull
    public String key;

    @Nonnull
    public String valueType;

    Object value;

    @Nonnull
    public String description;

    public boolean isSaasProtected;

    public boolean isDifferentAcrossTenants;

    public boolean isConfigYamlOnly;

    @Nullable
    public String[] possibleValues;

    public boolean isNullable;

    public Object defaultValue;

    public boolean isPluginProperty;

    public ConfigFieldInfo(@Nonnull String key, @Nonnull String valueType, JsonElement value, @Nonnull String description,
                           boolean isSaasProtected, boolean isDifferentAcrossTenants, boolean isConfigYamlOnly,
                           @Nullable String[] possibleValues, boolean isNullable, Object defaultValue, boolean isPluginProperty) {
        this.key = key;
        this.valueType = valueType;
        this.value = value;
        this.description = description;
        this.isSaasProtected = isSaasProtected;
        this.isDifferentAcrossTenants = isDifferentAcrossTenants;
        this.isConfigYamlOnly = isConfigYamlOnly;
        this.possibleValues = possibleValues;
        this.isNullable = isNullable;
        this.defaultValue = defaultValue;
        this.isPluginProperty = isPluginProperty;
    }
}
