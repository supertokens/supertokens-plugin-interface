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

    public boolean isDifferentAcrossTenants;

    @Nullable
    public String[] possibleValues;

    public boolean isNullable;

    public Object defaultValue;

    public boolean isPluginProperty;

    public boolean isPluginPropertyEditable;

    public ConfigFieldInfo(@Nonnull String key, @Nonnull String valueType, JsonElement value, @Nonnull String description,
                           boolean isDifferentAcrossTenants,
                           @Nullable String[] possibleValues, boolean isNullable, Object defaultValue,
                           boolean isPluginProperty, boolean isPluginPropertyEditable) {
        this.key = key;
        this.valueType = valueType;
        this.value = value;
        this.description = description;
        this.isDifferentAcrossTenants = isDifferentAcrossTenants;
        this.possibleValues = possibleValues;
        this.isNullable = isNullable;
        this.defaultValue = defaultValue;
        this.isPluginProperty = isPluginProperty;
        this.isPluginPropertyEditable = isPluginPropertyEditable;
    }
}
