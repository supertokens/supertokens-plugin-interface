package io.supertokens.pluginInterface.totp.sqlStorage;

import java.sql.SQLException;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;
import io.supertokens.pluginInterface.totp.TOTPStorage;

public interface TOTPSQLStorage extends TOTPStorage, SQLStorage {
    public int deleteDevice_Transaction(TransactionConnection con, String userId, String deviceName)
            throws StorageQueryException;

    public int getDevicesCount_Transaction(TransactionConnection con, String userId)
            throws StorageQueryException;

    public void removeUser_Transaction(TransactionConnection con, String userId)
            throws StorageQueryException;

}
