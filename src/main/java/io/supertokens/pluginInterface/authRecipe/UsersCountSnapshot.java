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
 *
 */
package io.supertokens.pluginInterface.authRecipe;

/**
 * A user count with the time it was counted, as returned by {@link UsersCountCacheStorage}.
 */
public class UsersCountSnapshot {

    public final long count;

    // When the count started (epoch ms). The count reflects the data as of about this moment.
    public final long countedAtMs;

    // True if this call ran the count; false if it returned a count stored by an earlier call.
    public final boolean newlyCounted;

    public UsersCountSnapshot(long count, long countedAtMs, boolean newlyCounted) {
        this.count = count;
        this.countedAtMs = countedAtMs;
        this.newlyCounted = newlyCounted;
    }
}
