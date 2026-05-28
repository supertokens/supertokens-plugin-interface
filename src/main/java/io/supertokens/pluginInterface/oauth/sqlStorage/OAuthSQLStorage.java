
package io.supertokens.pluginInterface.oauth.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.oauth.OAuthStorage;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

/**
 * SQL-specific OAuth storage methods that require transactional access.
 *
 * <p>These methods are used to implement the DB-level mutex for non-rotating
 * refresh token exchange: a {@code SELECT ... FOR UPDATE} on the oauth_sessions
 * row keeps all other instances waiting until the Hydra round-trip and the
 * subsequent mapping update are committed together.
 */
public interface OAuthSQLStorage extends OAuthStorage, SQLStorage {

    /**
     * Reads {@code internal_refresh_token} for the given {@code externalRefreshToken}
     * inside an open transaction, acquiring a row-level exclusive lock
     * ({@code SELECT ... FOR UPDATE}).  The lock is held until the caller
     * either commits or rolls back the surrounding transaction.
     *
     * @return the internal refresh token, or {@code null} if no mapping row exists
     *         (caller should fall back to using the external token as-is)
     */
    String getRefreshTokenMappingForUpdate_Transaction(AppIdentifier appIdentifier,
                                                       TransactionConnection con,
                                                       String externalRefreshToken)
            throws StorageQueryException;

    /**
     * Updates {@code internal_refresh_token}, {@code session_handle}, {@code jti},
     * and {@code exp} for the row identified by {@code externalRefreshToken}, inside
     * the same open transaction that previously called
     * {@link #getRefreshTokenMappingForUpdate_Transaction}.
     *
     * <p>The {@code external_refresh_token} column itself is intentionally left
     * unchanged — the client keeps using the same external token indefinitely.
     */
    void updateOAuthSessionInternal_Transaction(AppIdentifier appIdentifier,
                                                TransactionConnection con,
                                                String gid,
                                                String newInternalRefreshToken,
                                                String sessionHandle,
                                                String jti,
                                                long exp)
            throws StorageQueryException;
}
