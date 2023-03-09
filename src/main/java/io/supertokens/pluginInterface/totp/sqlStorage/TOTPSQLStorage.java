package io.supertokens.pluginInterface.totp.sqlStorage;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.totp.TOTPStorage;
import io.supertokens.pluginInterface.totp.TOTPUsedCode;
import io.supertokens.pluginInterface.totp.exception.TotpNotEnabledException;
import io.supertokens.pluginInterface.totp.exception.UsedCodeAlreadyExistsException;

public interface TOTPSQLStorage extends TOTPStorage, SQLStorage {
    public int deleteDevice_Transaction(TransactionConnection con, String userId, String deviceName)
            throws StorageQueryException;

    public int getDevicesCount_Transaction(TransactionConnection con, String userId)
            throws StorageQueryException;

    public void removeUser_Transaction(TransactionConnection con, String userId)
            throws StorageQueryException;

    /**
     * Get totp used codes for user (expired/non-expired) yet (sorted by descending
     * order of created time):
     */
    public TOTPUsedCode[] getAllUsedCodesDescOrderAndLockByUser_Transaction(TransactionConnection con,
            String userId)
            throws StorageQueryException;

    /** Insert a used TOTP code for an existing user: */
    void insertUsedCode_Transaction(TransactionConnection con, TOTPUsedCode code)
            throws StorageQueryException, TotpNotEnabledException, UsedCodeAlreadyExistsException;

}
