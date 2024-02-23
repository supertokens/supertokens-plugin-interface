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

import javax.annotation.Nonnull;

public class ConfigFieldInfo {
    @Nonnull
    public String name;
    @Nonnull
    public String description;
    public boolean isDifferentAcrossTenants;
    @Nonnull
    public String type;
    public String[] options;

    public ConfigFieldInfo(String name, String description, boolean isDifferentAcrossTenants, String type) {
        this(name, description, isDifferentAcrossTenants, type, null);
    }

    public ConfigFieldInfo(String name, String description, boolean isDifferentAcrossTenants, String type, String[] options) {
        this.name = name;
        this.description = description;
        this.isDifferentAcrossTenants = isDifferentAcrossTenants;
        this.type = type;
        this.options = options;
    }
}
