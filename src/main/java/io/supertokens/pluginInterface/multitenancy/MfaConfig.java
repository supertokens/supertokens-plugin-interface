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

import java.util.Set;

public class MfaConfig {
    public final String[] firstFactors;
    public final String[] defaultMFARequirements;

    public MfaConfig(String[] firstFactors, String[] defaultMFARequirements) {
        this.firstFactors = firstFactors == null ? new String[0] : firstFactors;
        this.defaultMFARequirements = defaultMFARequirements == null ? new String[0] : defaultMFARequirements;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MfaConfig) {
            MfaConfig otherTotpConfig = (MfaConfig) other;
            Set<String> thisFirstFactors = Set.of(this.firstFactors);
            Set<String> otherFirstFactors = Set.of(otherTotpConfig.firstFactors);

            Set<String> thisDefaultMFARequirements = Set.of(this.defaultMFARequirements);
            Set<String> otherDefaultMFARequirements = Set.of(otherTotpConfig.defaultMFARequirements);

            return thisFirstFactors.equals(otherFirstFactors) && thisDefaultMFARequirements.equals(otherDefaultMFARequirements);
        }
        return false;
    }
}
