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

package io.supertokens.pluginInterface.accountinfo;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.authRecipe.exceptions.AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.InputUserIdIsNotAPrimaryUserException;
import io.supertokens.pluginInterface.authRecipe.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.useridmapping.LockedUser;

/**
 * Storage interface for account info operations that require LockedUser.
 * Methods will be added by ISO-002 through ISO-007 tickets.
 *
 * Both PostgreSQL plugin Start.java and InMemoryDB Start.java must implement this interface.
 */
public interface AccountInfoStorage extends Storage {
    // Methods to be added by:
    // - ISO-002: reserveAccountInfoForLinking_Transaction (done)
    // - ISO-003: updateAccountInfo_Transaction
    // - ISO-004: addPrimaryUserAccountInfo_Transaction
    // - ISO-005: removeAccountInfoReservationForPrimaryUserForUnlinking_Transaction
    // - ISO-006: addTenantIdToRecipeUser_Transaction
    // - ISO-007: addTenantIdToPrimaryUser_Transaction

    /**
     * Reserves account info for linking a recipe user to a primary user.
     * This method requires LockedUser parameters to ensure proper locking has been acquired.
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param recipeUser The locked recipe user being linked
     * @param primaryUser The locked primary user to link to
     * @return true if the link was newly created, false if already linked to the same primary
     * @throws StorageQueryException on database errors
     * @throws UnknownUserIdException if either user does not exist
     * @throws InputUserIdIsNotAPrimaryUserException if the primary user is not actually a primary user
     * @throws CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException if recipe user is already linked to a different primary
     * @throws AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException if account info conflicts with another primary user
     */
    boolean reserveAccountInfoForLinking_Transaction(
            AppIdentifier appIdentifier,
            TransactionConnection con,
            LockedUser recipeUser,
            LockedUser primaryUser)
            throws StorageQueryException, UnknownUserIdException,
            InputUserIdIsNotAPrimaryUserException,
            CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;
}
