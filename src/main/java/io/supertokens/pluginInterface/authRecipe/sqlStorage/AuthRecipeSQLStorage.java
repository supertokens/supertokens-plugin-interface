/*
 *    Copyright (c) 2020, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.pluginInterface.authRecipe.sqlStorage;

import java.util.List;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.authRecipe.AuthRecipeUserInfo;
import io.supertokens.pluginInterface.authRecipe.exceptions.AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException;
import io.supertokens.pluginInterface.authRecipe.exceptions.InputUserIdIsNotAPrimaryUserException;
import io.supertokens.pluginInterface.authRecipe.exceptions.UnknownUserIdException;
import io.supertokens.pluginInterface.bulkimport.PrimaryUser;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.exceptions.StorageTransactionLogicException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface AuthRecipeSQLStorage extends AuthRecipeStorage, SQLStorage {

    AuthRecipeUserInfo getPrimaryUserById_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                      String userId)
            throws StorageQueryException;

    AuthRecipeUserInfo getPrimaryUserByWebauthNCredentialId_Transaction(TenantIdentifier tenantIdentifier, TransactionConnection con,
                                                                        String credentialId)
            throws StorageQueryException;

    // locks on thirdparty table
    AuthRecipeUserInfo[] listPrimaryUsersByThirdPartyInfo_Transaction(AppIdentifier appIdentifier,
                                                                      TransactionConnection con, String thirdPartyId,
                                                                      String thirdPartyUserId)
            throws StorageQueryException;

    boolean makePrimaryUser_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String userId)
            throws StorageQueryException, UnknownUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException,
            CannotBecomePrimarySinceRecipeUserIdAlreadyLinkedWithPrimaryUserIdException;

    boolean linkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String recipeUserId,
                                  String primaryUserId) throws StorageQueryException, UnknownUserIdException,
            InputUserIdIsNotAPrimaryUserException, CannotLinkSinceRecipeUserIdAlreadyLinkedWithAnotherPrimaryUserIdException,
            AccountInfoAlreadyAssociatedWithAnotherPrimaryUserIdException;

    void unlinkAccounts_Transaction(AppIdentifier appIdentifier, TransactionConnection con, String primaryUserId,
                                    String recipeUserId)
            throws StorageQueryException;

    boolean doesUserIdExist_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String externalUserId)
            throws StorageQueryException;

    void deleteAccountInfoReservations_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    void reservePrimaryUserAccountInfos_Transaction(TransactionConnection con, List<PrimaryUser> primaryUsers)
            throws StorageQueryException, StorageTransactionLogicException;

    /**
     * Batch-normalizes {@code primary_or_recipe_user_time_joined} for the given primary users so that,
     * within each linked group, every row shares a single value equal to the group's minimum
     * {@code time_joined}.
     * <p>
     * For each id in {@code primaryUserIds} this sets
     * {@code primary_or_recipe_user_time_joined = MIN(time_joined)} across all rows of that linked group,
     * in every table the storage maintains that carries the column (respecting the storage's
     * migration-mode branching), all inside the provided transaction.
     * <p>
     * This maintains the invariant that within a linked group every row shares
     * {@code primary_or_recipe_user_time_joined = group MIN}. User-list pagination orders and cursors on
     * that column while the next-page token is derived from the group's minimum {@code time_joined}; the
     * two agree only while this invariant holds, so a violation breaks pagination (early termination when
     * walking newest-first, an infinite token cycle when walking oldest-first). Live account linking
     * normalizes as part of its own storage update; this method exposes the same normalization for callers
     * that insert linked members without it — notably bulk import, which is expected to call this once per
     * batch after all login methods (across all recipes) have been inserted.
     *
     * @param appIdentifier  the app the primary users belong to
     * @param con            the transaction to run the updates in
     * @param primaryUserIds the primary user ids whose linked groups should be normalized
     */
    void updateTimeJoinedForPrimaryUsers_Transaction(AppIdentifier appIdentifier, TransactionConnection con,
                                                     List<String> primaryUserIds)
            throws StorageQueryException;

    /**
     * Counts the distinct primary users present in the tenant whose
     * {@code primary_or_recipe_user_time_joined} is strictly greater than {@code sinceMs} — the "joined
     * since" delta over the exact count.
     * <p>
     * A linked group's {@code primary_or_recipe_user_time_joined} is the {@code MIN} of {@code time_joined}
     * over its members (the invariant maintained by
     * {@link #updateTimeJoinedForPrimaryUsers_Transaction}). A user linked into a pre-existing group therefore
     * inherits a time {@code <= sinceMs} and correctly drops out of this delta — the group was already
     * counted in the anchor, so counting it here would double-count it. This is the same min-time adjacency
     * that user-list pagination relies on.
     * <p>
     * This method makes no promise about deletions, link-merges or unlinks: it counts what is present now
     * whose group-minimum join time falls after {@code sinceMs}. It is the live half of the approximate
     * user-count serving path, paired with an anchor from {@link #computeTenantUserCountAnchor} that was
     * taken with the same {@code sinceMs}.
     *
     * @param tenantIdentifier the tenant whose users to count
     * @param sinceMs          the exclusive lower bound on the group-minimum join time, in epoch
     *                         milliseconds
     * @return the number of distinct primary users in the tenant with group-minimum join time
     *         {@code > sinceMs}
     */
    long countTenantUsersJoinedSince(TenantIdentifier tenantIdentifier, long sinceMs)
            throws StorageQueryException;

    /**
     * Computes the anchor for the approximate tenant user count: the exact user count as of a single
     * snapshot, rebased onto {@code sinceMs} so it can be served together with a live
     * {@link #countTenantUsersJoinedSince} delta taken at any later moment.
     * <p>
     * The computation runs inside ONE read-only {@code REPEATABLE READ} transaction and, at that single
     * snapshot, evaluates:
     * <ul>
     *   <li>{@code C}  = the storage's exact tenant user count (as returned by
     *       {@link AuthRecipeStorage#getUsersCount}); and</li>
     *   <li>{@code d0} = {@link #countTenantUsersJoinedSince}{@code (tenantIdentifier, sinceMs)} evaluated at
     *       that same snapshot,</li>
     * </ul>
     * and returns {@code C - d0}.
     * <p>
     * Taking both counts at one snapshot is the exactness mechanism. It converts the snapshot-relative count
     * {@code C} into the timestamp-relative anchor {@code C - d0}: every user partitions exactly on whether
     * its group-minimum join time is {@code <= sinceMs} (in the anchor) or {@code > sinceMs} (in the delta),
     * with no dependence on how commit order interleaves with the snapshot. Consequently, serving
     * {@code anchor + countTenantUsersJoinedSince(tenantIdentifier, sinceMs)} at any later moment counts every
     * user exactly once for creations, regardless of clock-vs-commit ordering around the snapshot.
     * <p>
     * The caller is expected to supply {@code sinceMs} slightly in the past (a clock-skew margin so that the
     * delta window comfortably covers every not-yet-visible in-flight insert); the contract does not depend
     * on how far in the past it is. As with {@link #countTenantUsersJoinedSince}, this method promises nothing
     * about deletions, link-merges or unlinks that happen after the snapshot — that lag is the documented
     * approximation of the fast path.
     *
     * @param tenantIdentifier the tenant whose anchor to compute
     * @param sinceMs          the same epoch-millisecond boundary that will be passed to
     *                         {@link #countTenantUsersJoinedSince} when serving the live delta
     * @return {@code C - d0}: the exact count as of the snapshot minus the users that joined after
     *         {@code sinceMs}
     */
    long computeTenantUserCountAnchor(TenantIdentifier tenantIdentifier, long sinceMs)
            throws StorageQueryException;
}
