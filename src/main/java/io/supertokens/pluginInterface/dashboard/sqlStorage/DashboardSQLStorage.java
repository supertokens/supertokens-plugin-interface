package io.supertokens.pluginInterface.dashboard.sqlStorage;

import io.supertokens.pluginInterface.dashboard.DashboardStorage;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface DashboardSQLStorage extends DashboardStorage, SQLStorage {

    void updateDashboardUsersEmailWithEmail_Transaction(TransactionConnection con, String email, String newEmail) throws StorageQueryException, DuplicateEmailException;

    void updateDashboardUsersPasswordWithEmail_Transaction(TransactionConnection con, String email, String newPassword) throws StorageQueryException;

    void updateDashboardUsersEmailWithUserId_Transaction(TransactionConnection con, String userId, String newEmail) throws StorageQueryException, DuplicateEmailException;

    void updateDashboardUsersPasswordWithUserId_Transaction(TransactionConnection con, String userId, String newPassword) throws StorageQueryException;
    
}