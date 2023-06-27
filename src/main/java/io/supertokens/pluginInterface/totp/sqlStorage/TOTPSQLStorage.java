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
import io.supertokens.pluginInterface.totp.exception.UsedCodeAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPSQLStorage extends TOTPStorage, SQLStorage {
    public int deleteDevice_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId,
                                        String deviceName)
            throws StorageQueryException;

    public TOTPDevice[] getDevices_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    public void removeUser_Transaction(TransactionConnection con, AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    public boolean removeUser(TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Get totp used codes for user (expired/non-expired) yet (sorted by descending
     * order of created time):
     */
    public TOTPUsedCode[] getAllUsedCodesDescOrder_Transaction(TransactionConnection con,
                                                               TenantIdentifier tenantIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Insert a used TOTP code for an existing user:
     */
    void insertUsedCode_Transaction(TransactionConnection con, TenantIdentifier tenantIdentifier, TOTPUsedCode code)
            throws StorageQueryException, UnknownDeviceException, UsedCodeAlreadyExistsException,
            TenantOrAppNotFoundException;

}
