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

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class MfaConfig {
    @Nullable
    public final String[] firstFactors;

    @Nullable
    public final String[] defaultRequiredFactorIds;

    public MfaConfig(@Nullable String[] firstFactors, @Nullable String[] defaultRequiredFactorIds) {
        this.firstFactors = firstFactors;
        this.defaultRequiredFactorIds = defaultRequiredFactorIds;
    }

    private boolean compareStrArray(String[] arr1, String[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1 == null || arr2 == null) {
            return false;
        }

        Set<String> set1 = Set.of(arr1);
        Set<String> set2 = Set.of(arr2);

        return set1.equals(set2);
    }

    public JsonObject toJson() {
        return new GsonBuilder().serializeNulls().create().toJsonTree(this).getAsJsonObject();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof MfaConfig)) {
            return false;
        }

        return compareStrArray(this.firstFactors, ((MfaConfig) other).firstFactors) &&
                compareStrArray(this.defaultRequiredFactorIds, ((MfaConfig) other).defaultRequiredFactorIds);
    }
}
