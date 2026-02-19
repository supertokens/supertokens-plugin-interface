/*
 *    Copyright (c) 2024, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.useridmapping;

import javax.annotation.Nonnull;

/**
 * Holds two locked users for linking operations.
 * Ensures both users are locked before linking can proceed.
 */
public class LockedUserPair {
    @Nonnull
    private final LockedUser recipeUser;
    @Nonnull
    private final LockedUser primaryUser;

    public LockedUserPair(@Nonnull LockedUser recipeUser, @Nonnull LockedUser primaryUser) {
        this.recipeUser = recipeUser;
        this.primaryUser = primaryUser;
    }

    @Nonnull
    public LockedUser getRecipeUser() {
        return recipeUser;
    }

    @Nonnull
    public LockedUser getPrimaryUser() {
        return primaryUser;
    }
}
