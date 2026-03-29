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
    public static enum RESULT {
        OK, WAS_ALREADY_A_PRIMARY_USER, LINKED_WITH_ANOTHER_PRIMARY_USER, CONFLICTING_ACCOUNT_INFO
    }

    public final RESULT status;
    public final String conflictingPrimaryUserId;
    public final String message;

    private CanBecomePrimaryResult(RESULT status, String conflictingPrimaryUserId, String message) {
        this.status = status;
        this.conflictingPrimaryUserId = conflictingPrimaryUserId;
        this.message = message;
    }

    public static CanBecomePrimaryResult okResult() {
        return new CanBecomePrimaryResult(RESULT.OK, null, null);
    }

    public static CanBecomePrimaryResult wasAlreadyAPrimaryUserResult() {
        return new CanBecomePrimaryResult(RESULT.WAS_ALREADY_A_PRIMARY_USER, null, null);
    }

    public static CanBecomePrimaryResult linkedWithAnotherPrimaryUserResult(String conflictingPrimaryUserId) {
        return new CanBecomePrimaryResult(RESULT.LINKED_WITH_ANOTHER_PRIMARY_USER, conflictingPrimaryUserId, null);
    }

    public static CanBecomePrimaryResult conflictingAccountInfoResult(String conflictingPrimaryUserId, String message) {
        return new CanBecomePrimaryResult(RESULT.CONFLICTING_ACCOUNT_INFO, conflictingPrimaryUserId, message);
    }
}
