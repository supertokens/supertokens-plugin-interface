/*
 *    Copyright (c) 2026, VRAI Labs and/or its affiliates. All rights reserved.
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TenantConfigSerializerTest {

    private static final String[] ALL_BUILT_IN_FACTORS = new String[]{
            "emailpassword", "thirdparty", "otp-email", "otp-phone", "link-email", "link-phone"};

    private static TenantConfig tenant(boolean emailPassword, boolean thirdParty, boolean passwordless,
                                       String[] firstFactors) {
        return new TenantConfig(new TenantIdentifier(null, null, "t1"),
                new EmailPasswordConfig(emailPassword),
                new ThirdPartyConfig(thirdParty, new ThirdPartyConfig.Provider[0]),
                new PasswordlessConfig(passwordless),
                firstFactors, null, new JsonObject());
    }

    private static Set<String> v2FirstFactors(TenantConfig config) {
        JsonObject json = config.toJson_v2_5_1(false, null, new String[0]);
        if (!json.has("firstFactors")) {
            return null;
        }
        Set<String> result = new HashSet<>();
        for (JsonElement e : json.get("firstFactors").getAsJsonArray()) {
            result.add(e.getAsString());
        }
        return result;
    }

    private static boolean enabledIn4_0(TenantConfig config, String factor) {
        JsonObject json = config.toJsonLesserThanOrEqualTo4_0(false, null, new String[0]);
        switch (factor) {
            case "emailpassword":
                return json.get("emailPassword").getAsJsonObject().get("enabled").getAsBoolean();
            case "thirdparty":
                return json.get("thirdParty").getAsJsonObject().get("enabled").getAsBoolean();
            default:
                return json.get("passwordless").getAsJsonObject().get("enabled").getAsBoolean();
        }
    }

    @Test
    public void testIssue233_disabledRecipeFactorsRemovedFromV2Output() {
        // email password disabled while an explicit firstFactors list still names it
        assertEquals(Set.of("thirdparty"),
                v2FirstFactors(tenant(false, true, true, new String[]{"emailpassword", "thirdparty"})));

        // third party disabled
        assertEquals(Set.of("emailpassword", "otp-email"),
                v2FirstFactors(tenant(true, false, true, new String[]{"emailpassword", "thirdparty", "otp-email"})));

        // passwordless disabled: all passwordless factor ids are dropped, custom factors are kept
        assertEquals(Set.of("emailpassword", "custom-factor"),
                v2FirstFactors(tenant(true, true, false, new String[]{"emailpassword", "otp-email", "otp-phone",
                        "link-email", "link-phone", "custom-factor"})));

        // when every listed factor belongs to a disabled recipe, the output is an empty list (nothing allowed),
        // not a missing firstFactors (which would mean everything is allowed)
        JsonObject json = tenant(false, true, true, new String[]{"emailpassword"})
                .toJson_v2_5_1(false, null, new String[0]);
        assertTrue(json.has("firstFactors"));
        assertEquals(new JsonArray(), json.get("firstFactors"));

        // the stored config is not modified by serialising it
        TenantConfig config = tenant(false, true, true, new String[]{"emailpassword", "thirdparty"});
        config.toJson_v2_5_1(false, null, new String[0]);
        assertEquals(List.of("emailpassword", "thirdparty"), List.of(config.firstFactors));
    }

    @Test
    public void v2OutputIsUnchangedWhenFlagsAndFirstFactorsAgree() {
        // all recipes enabled: explicit firstFactors are passed through as they are
        assertEquals(Set.of("emailpassword", "custom-factor"),
                v2FirstFactors(tenant(true, true, true, new String[]{"emailpassword", "custom-factor"})));
        // disabled recipes whose factors are not listed do not change the output
        assertEquals(Set.of("thirdparty"),
                v2FirstFactors(tenant(false, true, false, new String[]{"thirdparty"})));
        // empty firstFactors stays empty
        assertEquals(Set.of(), v2FirstFactors(tenant(false, false, false, new String[0])));
        // firstFactors == null: derived from the flags as before
        assertNull(v2FirstFactors(tenant(true, true, true, null)));
        assertEquals(Set.of("emailpassword"), v2FirstFactors(tenant(true, false, false, null)));
    }

    @Test
    public void v2OutputNeverListsABuiltInFactorThatOlderOutputsReportAsDisabled() {
        for (int flags = 0; flags < 8; flags++) {
            boolean ep = (flags & 1) != 0;
            boolean tp = (flags & 2) != 0;
            boolean pwless = (flags & 4) != 0;
            for (int mask = 0; mask < (1 << ALL_BUILT_IN_FACTORS.length); mask++) {
                List<String> ff = new ArrayList<>();
                for (int i = 0; i < ALL_BUILT_IN_FACTORS.length; i++) {
                    if ((mask & (1 << i)) != 0) {
                        ff.add(ALL_BUILT_IN_FACTORS[i]);
                    }
                }
                TenantConfig config = tenant(ep, tp, pwless, ff.toArray(new String[0]));
                Set<String> v2 = v2FirstFactors(config);
                for (String factor : ALL_BUILT_IN_FACTORS) {
                    boolean allowedInV2 = v2 == null || v2.contains(factor);
                    if (allowedInV2) {
                        assertTrue("flags=" + flags + " firstFactors=" + ff + " factor=" + factor,
                                enabledIn4_0(config, factor));
                    }
                }
                // nothing that is enabled and listed is dropped
                for (String factor : ff) {
                    if (enabledIn4_0(config, factor)) {
                        assertTrue(v2.contains(factor));
                    } else {
                        assertFalse(v2.contains(factor));
                    }
                }
            }
        }
    }
}
