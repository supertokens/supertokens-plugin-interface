package io.supertokens.pluginInterface.totp.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.exceptions.TenantOrAppNotFoundException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.totp.TOTPDevice;
import io.supertokens.pluginInterface.totp.TOTPStorage;
import io.supertokens.pluginInterface.totp.TOTPUsedCode;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.UnknownTotpUserIdException;
import io.supertokens.pluginInterface.totp.exception.UsedCodeAlreadyExistsException;

import java.util.List;

public interface TOTPSQLStorage extends TOTPStorage, SQLStorage {
    public int deleteDevice_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                        String deviceName)
            throws StorageQueryException;

    /**
     * Get the devices of a user inside a transaction. Implementations lock the returned device rows until the
     * transaction ends, so concurrent transactions working on the same user's devices are serialised.
     */
    public TOTPDevice[] getDevices_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    public void removeUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    public boolean removeUser(TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Get totp used codes for user (expired/non-expired) yet (sorted by descending
     * order of created time):
     *
     * @deprecated only returns the codes used in one tenant, while TOTP devices are shared by all tenants of the
     * app. Use {@link #getAllUsedCodesDescOrder_Transaction(TransactionConnection, AppIdentifier, String)} for
     * replay protection and rate limiting.
     */
    @Deprecated
    public TOTPUsedCode[] getAllUsedCodesDescOrder_Transaction(TransactionConnection con,
                                                               TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Get the totp used codes (expired/non-expired) of a user across all tenants of the app, sorted by descending
     * order of created time. This is the state that replay protection and rate limiting must be computed from,
     * because a user's TOTP devices are app-wide.
     *
     * Implementations must, before reading, take a lock scoped to (appId, userId) that is held until the
     * transaction ends, so that concurrent callers for the same user are serialised. The read must happen after
     * the lock is acquired, so that it includes the codes committed by a transaction that held the lock before.
     */
    TOTPUsedCode[] getAllUsedCodesDescOrder_Transaction(TransactionConnection con, AppIdentifier appIdentifier,
                                                        String userId)
            throws StorageQueryException;

    /**
     * Insert a used TOTP code for an existing user:
     */
    void insertUsedCode_Transaction(TransactionConnection con, TenantIdentifier tenantIdentifier, TOTPUsedCode code)
            throws StorageQueryException, UnknownTotpUserIdException, UsedCodeAlreadyExistsException,
            TenantOrAppNotFoundException;

    TOTPDevice getDeviceByName_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                           String deviceName) throws StorageQueryException;

    TOTPDevice createDevice_Transaction(TransactionConnection con, AppIdentifier appIdentifier, TOTPDevice device)
            throws StorageQueryException, DeviceAlreadyExistsException, TenantOrAppNotFoundException;

    void createDevices_Transaction(TransactionConnection con, AppIdentifier appIdentifier, List<TOTPDevice> devices)
            throws StorageQueryException, TenantOrAppNotFoundException;
}
