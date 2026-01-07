/*
 *    Copyright (c) 2025, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.authRecipe;

public class CanBecomePrimaryResult {
    public final boolean ok;
    public final String conflictingPrimaryUserId;
    public final String message;

    private CanBecomePrimaryResult(boolean ok, String conflictingPrimaryUserId, String message) {
        this.ok = ok;
        this.conflictingPrimaryUserId = conflictingPrimaryUserId;
        this.message = message;
    }

    public static CanBecomePrimaryResult okResult() {
        return new CanBecomePrimaryResult(true, null, null);
    }

    public static CanBecomePrimaryResult notOkResult(String conflictingPrimaryUserId, String message) {
        return new CanBecomePrimaryResult(false, conflictingPrimaryUserId, message);
    }
}
