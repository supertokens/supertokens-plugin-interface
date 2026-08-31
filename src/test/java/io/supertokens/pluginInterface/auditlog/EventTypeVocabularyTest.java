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

package io.supertokens.pluginInterface.auditlog;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Pins the activity-log event-type vocabulary shared across core and the storage plugins. A drift here
 * silently disables the last-active rollup, so the fold set is asserted by literal name (both what is
 * included and what is excluded), the two enum vocabularies round-trip through {@code fromValue}, and the
 * activity and lifecycle vocabularies are proven disjoint.
 */
public class EventTypeVocabularyTest {

    // ---- fold-set membership, pinned by name ----

    @Test
    public void foldSetContainsExactlyTheEightCreditingTypes() {
        Set<String> expected = new HashSet<>();
        // the six semantic activity events
        expected.add("sign_in");
        expected.add("token_refresh");
        expected.add("session_create");
        expected.add("sign_out");
        expected.add("oauth_token_exchange");
        expected.add("oauth_authorize");
        // plus the two activity-implying lifecycle events
        expected.add("user_creation");
        expected.add("account_linking");

        assertEquals(expected, new HashSet<>(RollupEventTypes.FOLD_SET));
        assertEquals(8, RollupEventTypes.FOLD_SET.size());
    }

    @Test
    public void foldSetIncludesEverySemanticActivityEventByName() {
        assertTrue(RollupEventTypes.FOLD_SET.contains("sign_in"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("token_refresh"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("session_create"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("sign_out"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("oauth_token_exchange"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("oauth_authorize"));
    }

    @Test
    public void foldSetIncludesTheTwoActivityImplyingLifecycleEventsByName() {
        assertTrue(RollupEventTypes.FOLD_SET.contains("user_creation"));
        assertTrue(RollupEventTypes.FOLD_SET.contains("account_linking"));
    }

    @Test
    public void foldSetExcludesUserImportAndTheRetiredSyntheticEvent() {
        assertFalse("imported != active (supertokens-core#1403)", RollupEventTypes.FOLD_SET.contains("user_import"));
        assertFalse("user_last_active is retired, no writer remains",
                RollupEventTypes.FOLD_SET.contains("user_last_active"));
    }

    @Test
    public void foldSetExcludesEveryOtherLifecycleEventByName() {
        assertFalse(RollupEventTypes.FOLD_SET.contains("account_unlinking"));
        assertFalse(RollupEventTypes.FOLD_SET.contains("user_deletion"));
        assertFalse(RollupEventTypes.FOLD_SET.contains("user_group_deletion"));
        assertFalse(RollupEventTypes.FOLD_SET.contains("tenant_association"));
        assertFalse(RollupEventTypes.FOLD_SET.contains("tenant_disassociation"));
    }

    @Test
    public void foldSetIsUnmodifiable() {
        try {
            RollupEventTypes.FOLD_SET.add("something_else");
            fail("expected the fold set to be unmodifiable");
        } catch (UnsupportedOperationException expected) {
            // the set is an immutable single source of truth
        }
    }

    @Test
    public void sqlInListQuotesEveryFoldValueInOrder() {
        assertEquals(
                "'sign_in', 'token_refresh', 'session_create', 'sign_out', 'oauth_token_exchange', "
                        + "'oauth_authorize', 'user_creation', 'account_linking'",
                RollupEventTypes.sqlInList());
    }

    // ---- fromValue round-trip, both enums ----

    @Test
    public void activityEventTypeRoundTripsThroughFromValue() {
        for (ActivityEventType type : ActivityEventType.values()) {
            assertSame(type, ActivityEventType.fromValue(type.getValue()));
        }
        assertNull(ActivityEventType.fromValue("user_creation")); // a lifecycle value
        assertNull(ActivityEventType.fromValue("not_an_event"));
        assertNull(ActivityEventType.fromValue(null));
        assertFalse(ActivityEventType.isActivityEvent("user_creation"));
        assertTrue(ActivityEventType.isActivityEvent("sign_in"));
    }

    @Test
    public void lifecycleEventTypeRoundTripsThroughFromValue() {
        for (LifecycleEventType type : LifecycleEventType.values()) {
            assertSame(type, LifecycleEventType.fromValue(type.getValue()));
        }
        assertNull(LifecycleEventType.fromValue("sign_in")); // an activity value
        assertNull(LifecycleEventType.fromValue("not_an_event"));
        assertNull(LifecycleEventType.fromValue(null));
        assertFalse(LifecycleEventType.isLifecycleEvent("sign_in"));
        assertTrue(LifecycleEventType.isLifecycleEvent("user_creation"));
    }

    // ---- the two vocabularies are disjoint ----

    @Test
    public void activityAndLifecycleVocabulariesAreDisjoint() {
        Set<String> activity = new HashSet<>();
        for (ActivityEventType type : ActivityEventType.values()) {
            activity.add(type.getValue());
        }
        for (LifecycleEventType type : LifecycleEventType.values()) {
            assertFalse("'" + type.getValue() + "' is in both vocabularies",
                    activity.contains(type.getValue()));
            assertNull("'" + type.getValue() + "' resolves as an activity event",
                    ActivityEventType.fromValue(type.getValue()));
        }
        for (ActivityEventType type : ActivityEventType.values()) {
            assertNull("'" + type.getValue() + "' resolves as a lifecycle event",
                    LifecycleEventType.fromValue(type.getValue()));
        }
    }
}
