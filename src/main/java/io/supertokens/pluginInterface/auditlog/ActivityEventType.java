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

/**
 * The vocabulary of "activity" events written to the activity log — user-initiated interactions that mark
 * a user as recently active and feed the last-active rollup fold ({@code last_active(user) = MAX(created_at)}
 * over the {@link RollupEventTypes#FOLD_SET fold set}).
 *
 * <p>These events form a distinct class from {@link LifecycleEventType lifecycle events}: activity events are
 * best-effort/throttleable pings emitted outside any mutation transaction, whereas lifecycle events are
 * mandatory count-affecting mutations recorded in their own transaction. They replaced the single synthetic
 * {@code user_last_active} event, which is retired: the concrete interaction (a sign-in, a refresh, a session
 * create, ...) is now recorded directly and the fold counts it.
 *
 * <p>The string {@link #getValue() value} is what lands in the {@code activity_log.event_type} column. This
 * class defines the vocabulary only — the emit sites, throttling and payload logic stay core-side.
 */
public enum ActivityEventType {

    /** An interactive sign-in of an existing user (emailpassword / webauthn / thirdparty / passwordless). */
    SIGN_IN("sign_in"),

    /** A session's tokens were refreshed. */
    TOKEN_REFRESH("token_refresh"),

    /** A new session was created for a user. */
    SESSION_CREATE("session_create"),

    /** A session was explicitly revoked for a user. */
    SIGN_OUT("sign_out"),

    /** An OAuth token exchange resolved to a session user. */
    OAUTH_TOKEN_EXCHANGE("oauth_token_exchange"),

    /** An OAuth authorization request resolved to a session user. */
    OAUTH_AUTHORIZE("oauth_authorize");

    private final String value;

    ActivityEventType(String value) {
        this.value = value;
    }

    /** The string stored in the {@code activity_log.event_type} column. */
    public String getValue() {
        return value;
    }

    /**
     * @return the activity event type with the given {@code event_type} value, or {@code null} if the
     * value is not an activity event (e.g. a lifecycle event such as {@code user_creation}).
     */
    public static ActivityEventType fromValue(String value) {
        if (value != null) {
            for (ActivityEventType type : values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
        }
        return null;
    }

    /** @return whether the given {@code event_type} value denotes an activity event. */
    public static boolean isActivityEvent(String value) {
        return fromValue(value) != null;
    }
}
