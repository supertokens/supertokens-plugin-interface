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

package io.supertokens.pluginInterface.authRecipe;

public class CanLinkAccountsResult {
    public static enum RESULT {
        OK, WAS_ALREADY_LINKED_TO_PRIMARY_USER, INPUT_USER_IS_NOT_PRIMARY_USER, RECIPE_USER_LINKED_TO_ANOTHER_PRIMARY_USER, ACCOUNT_INFO_ASSOCIATED_WITH_ANOTHER_PRIMARY_USER
    }
    public final RESULT status;
    public final String conflictingPrimaryUserId;
    public final String message;

    private CanLinkAccountsResult(RESULT status, String conflictingPrimaryUserId, String message) {
        this.status = status;
        this.conflictingPrimaryUserId = conflictingPrimaryUserId;
        this.message = message;
    }

    public static CanLinkAccountsResult okResult() {
        return new CanLinkAccountsResult(RESULT.OK, null, null);
    }

    public static CanLinkAccountsResult wasAlreadyLinkedToPrimaryUserResult() {
        return new CanLinkAccountsResult(RESULT.WAS_ALREADY_LINKED_TO_PRIMARY_USER, null, null);
    }

    public static CanLinkAccountsResult inputUserIsNotPrimaryUserResult() {
        return new CanLinkAccountsResult(RESULT.INPUT_USER_IS_NOT_PRIMARY_USER, null, null);
    }

    public static CanLinkAccountsResult recipeUserLinkedToAnotherPrimaryUserResult(String primaryUserId) {
        return new CanLinkAccountsResult(RESULT.RECIPE_USER_LINKED_TO_ANOTHER_PRIMARY_USER, primaryUserId, "The input recipe user ID is already linked to another user ID");
    }

    public static CanLinkAccountsResult notOkResult(String conflictingPrimaryUserId, String message) {
        return new CanLinkAccountsResult(RESULT.ACCOUNT_INFO_ASSOCIATED_WITH_ANOTHER_PRIMARY_USER, conflictingPrimaryUserId, message);
    }
}
