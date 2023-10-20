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

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other instanceof MfaConfig) {
            Set<String> thisFirstFactors = Set.of(this.firstFactors);
            Set<String> otherFirstFactors = Set.of(((MfaConfig) other).firstFactors);

            Set<String> thisdefaultRequiredFactorIds = Set.of(this.defaultRequiredFactorIds);
            Set<String> otherdefaultRequiredFactorIds = Set.of(((MfaConfig) other).defaultRequiredFactorIds);

            return thisFirstFactors.equals(otherFirstFactors) &&
                    thisdefaultRequiredFactorIds.equals(otherdefaultRequiredFactorIds);
        }

        return false;
    }
}
