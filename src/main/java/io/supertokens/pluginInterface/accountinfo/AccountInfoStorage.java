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
import io.supertokens.pluginInterface.authRecipe.ACCOUNT_INFO_TYPE;
import io.supertokens.pluginInterface.authRecipe.exceptions.AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.AnotherPrimaryUserWithEmailAlreadyExistsException;
import io.supertokens.pluginInterface.authRecipe.exceptions.AnotherPrimaryUserWithPhoneNumberAlreadyExistsException;
import io.supertokens.pluginInterface.authRecipe.exceptions.AnotherPrimaryUserWithThirdPartyInfoAlreadyExistsException;
import io.supertokens.pluginInterface.authRecipe.exceptions.CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.EmailChangeNotAllowedException;
import io.supertokens.pluginInterface.authRecipe.exceptions.InputUserIdIsNotAPrimaryUserException;
import io.supertokens.pluginInterface.authRecipe.exceptions.PhoneNumberChangeNotAllowedException;
import io.supertokens.pluginInterface.authRecipe.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.passwordless.exception.DuplicatePhoneNumberException;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.thirdparty.exception.DuplicateThirdPartyUserException;
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

    /**
     * Updates account info (email or phone number) for a user.
     * This method requires a LockedUser parameter to ensure proper locking has been acquired.
     *
     * When updating a linked user's account info, this method updates:
     * 1. The recipe_user_account_infos table
     * 2. The recipe_user_tenants table
     * 3. The primary_user_tenants table (if the user is linked to a primary)
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param user The locked user whose account info is being updated
     * @param accountInfoType The type of account info to update (EMAIL or PHONE_NUMBER only)
     * @param newAccountInfoValue The new value for the account info (null to remove)
     * @throws StorageQueryException on database errors
     * @throws UnknownUserIdException if the user does not exist
     * @throws EmailChangeNotAllowedException if email change would conflict with another primary user
     * @throws PhoneNumberChangeNotAllowedException if phone change would conflict with another primary user
     * @throws DuplicateEmailException if the email is already used by another user in the same tenant
     * @throws DuplicatePhoneNumberException if the phone number is already used by another user in the same tenant
     * @throws DuplicateThirdPartyUserException should never be thrown for EMAIL/PHONE_NUMBER types
     */
    void updateAccountInfo_Transaction(
            AppIdentifier appIdentifier,
            TransactionConnection con,
            LockedUser user,
            ACCOUNT_INFO_TYPE accountInfoType,
            String newAccountInfoValue)
            throws StorageQueryException, UnknownUserIdException,
            EmailChangeNotAllowedException, PhoneNumberChangeNotAllowedException,
            DuplicateEmailException, DuplicatePhoneNumberException, DuplicateThirdPartyUserException;

    /**
     * Adds account info entries to primary_user_tenants when a user becomes a primary user.
     * This method requires a LockedUser parameter to ensure proper locking has been acquired,
     * preventing race conditions during concurrent makePrimary operations.
     *
     * This method:
     * 1. Verifies the user is not already linked to another primary user
     * 2. Inserts the user's account info into primary_user_tenants table
     * 3. Updates recipe_user_account_infos to mark this user as a primary user
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param primaryUser The locked user who is becoming a primary user
     * @return true if the user newly became primary, false if already was primary
     * @throws StorageQueryException on database errors
     * @throws UnknownUserIdException if the user does not exist
     * @throws AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException if account info conflicts with another primary user
     * @throws CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException if user is already linked to another primary
     */
    boolean addPrimaryUserAccountInfo_Transaction(
            AppIdentifier appIdentifier,
            TransactionConnection con,
            LockedUser primaryUser)
            throws StorageQueryException, UnknownUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException,
            CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException;

    /**
     * Removes account info reservations from primary_user_tenants when unlinking a recipe user from a primary user.
     * This method requires a LockedUser parameter to ensure proper locking has been acquired,
     * preventing race conditions during concurrent unlink operations.
     *
     * The primary user ID is derived from recipeUser.getPrimaryUserId(), since lockUser()
     * acquires locks on both the recipe user and their primary user.
     *
     * This method:
     * 1. Verifies the recipe user is linked or is itself a primary user
     * 2. Removes entries from primary_user_tenants that are no longer needed after unlinking
     * 3. Updates recipe_user_account_infos to clear the primary_user_id reference
     *
     * The entries removed from primary_user_tenants are those where:
     * - The account info (type + value) is not present in any other linked recipe user's recipe_user_tenants, OR
     * - The tenant_id is not present in any other linked recipe user's recipe_user_tenants
     *
     * @param appIdentifier The app context
     * @param con The transaction connection
     * @param recipeUser The locked recipe user being unlinked (must be linked or primary)
     * @throws StorageQueryException on database errors
     * @throws IllegalStateException if the recipe user is not linked to any primary user
     */
    void removeAccountInfoReservationForPrimaryUserForUnlinking_Transaction(
            AppIdentifier appIdentifier,
            TransactionConnection con,
            LockedUser recipeUser)
            throws StorageQueryException;

    /**
     * Adds a tenant to a recipe user's tenant associations.
     * This method requires a LockedUser parameter to ensure proper locking has been acquired,
     * preventing race conditions during concurrent tenant association and linking operations.
     *
     * This method:
     * 1. Inserts the user's account info into recipe_user_tenants table for the specified tenant
     * 2. Handles duplicate account info conflicts appropriately
     *
     * @param tenantIdentifier The tenant to associate the user with (also provides app context)
     * @param con The transaction connection
     * @param user The locked user to associate with the tenant
     * @throws StorageQueryException on database errors
     * @throws DuplicateEmailException if the user's email conflicts with another user in the tenant
     * @throws DuplicateThirdPartyUserException if the user's third-party info conflicts with another user in the tenant
     * @throws DuplicatePhoneNumberException if the user's phone number conflicts with another user in the tenant
     */
    void addTenantIdToRecipeUser_Transaction(
            TenantIdentifier tenantIdentifier,
            TransactionConnection con,
            LockedUser user)
            throws StorageQueryException, DuplicateEmailException,
            DuplicateThirdPartyUserException, DuplicatePhoneNumberException;

    /**
     * Adds account info entries to primary_user_tenants when adding a tenant to a primary user.
     * This method is called when associating a user with a new tenant to ensure that primary user
     * reservations are properly updated.
     *
     * This method requires a LockedUser parameter to ensure proper locking has been acquired,
     * preventing race conditions during concurrent tenant association and linking operations.
     *
     * This method:
     * 1. Verifies the user is a primary user
     * 2. Inserts the user's account info into primary_user_tenants table for the specified tenant
     * 3. Handles conflicts with other primary users appropriately
     *
     * @param tenantIdentifier The tenant to associate the primary user's account info with (also provides app context)
     * @param con The transaction connection
     * @param primaryUser The locked primary user whose account info should be reserved for this tenant
     * @throws StorageQueryException on database errors
     * @throws AnotherPrimaryUserWithEmailAlreadyExistsException if the primary user's email conflicts with another primary user in the tenant
     * @throws AnotherPrimaryUserWithPhoneNumberAlreadyExistsException if the primary user's phone conflicts with another primary user in the tenant
     * @throws AnotherPrimaryUserWithThirdPartyInfoAlreadyExistsException if the primary user's third-party info conflicts with another primary user in the tenant
     * @throws IllegalStateException if the user is not a primary user
     */
    void addTenantIdToPrimaryUser_Transaction(
            TenantIdentifier tenantIdentifier,
            TransactionConnection con,
            LockedUser primaryUser)
            throws StorageQueryException,
            AnotherPrimaryUserWithEmailAlreadyExistsException,
            AnotherPrimaryUserWithPhoneNumberAlreadyExistsException,
            AnotherPrimaryUserWithThirdPartyInfoAlreadyExistsException;
}
