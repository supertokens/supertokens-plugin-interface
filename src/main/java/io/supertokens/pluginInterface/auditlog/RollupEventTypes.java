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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Single source of truth for the {@code activity_log.event_type} values that feed the last-active rollup
 * fold, shared by every side that reads or writes the fold so they cannot drift apart: the core's in-memory
 * fold/gate predicates and the SQL plugins' fold/gate queries (supertokens-postgresql-plugin#398). A drift
 * silently disables the rollup — the fold credits nobody and the last-active projection stops advancing — so
 * the set is defined once here, in the shared dependency of both sides.
 *
 * <p>The set is the six {@link ActivityEventType activity events} plus the two {@link LifecycleEventType
 * lifecycle events} that imply activity:
 * <ul>
 *   <li>{@link LifecycleEventType#USER_CREATION user_creation} — an interactive creation counts as activity;
 *       the fold reads it in place of a sign-up ping.</li>
 *   <li>{@link LifecycleEventType#ACCOUNT_LINKING account_linking} — credits the primary user; the reconcile
 *       separately drops the linked-away recipe user's row.</li>
 * </ul>
 *
 * <p>Everything else is excluded, deliberately:
 * <ul>
 *   <li>{@link LifecycleEventType#USER_IMPORT user_import} — an imported user is not thereby active
 *       (decided on supertokens-core#1403).</li>
 *   <li>every other {@link LifecycleEventType lifecycle event} — {@code account_unlinking}, {@code
 *       user_deletion}, {@code user_group_deletion}, {@code tenant_association}, {@code tenant_disassociation}
 *       are count-affecting bookkeeping, not activity.</li>
 *   <li>the retired {@code user_last_active} synthetic event — no writer remains (replaced by the concrete
 *       activity events above).</li>
 * </ul>
 */
public final class RollupEventTypes {

    private RollupEventTypes() {
    }

    /**
     * The {@code event_type} values the fold credits toward a user's recency, as an unmodifiable set.
     * Insertion order is preserved (the six activity events, then {@code user_creation}, then {@code
     * account_linking}).
     */
    public static final Set<String> FOLD_SET;

    static {
        Set<String> types = new LinkedHashSet<>();
        for (ActivityEventType type : ActivityEventType.values()) {
            types.add(type.getValue());
        }
        types.add(LifecycleEventType.USER_CREATION.getValue());
        types.add(LifecycleEventType.ACCOUNT_LINKING.getValue());
        FOLD_SET = Collections.unmodifiableSet(types);
    }

    /**
     * @return the fold set as a SQL {@code IN}-list body, e.g. {@code 'sign_in', 'token_refresh', ...}. Safe
     * to inline into a query string: every value is a compile-time enum constant, never user input.
     */
    public static String sqlInList() {
        return FOLD_SET.stream().map(v -> "'" + v + "'").collect(Collectors.joining(", "));
    }
}
