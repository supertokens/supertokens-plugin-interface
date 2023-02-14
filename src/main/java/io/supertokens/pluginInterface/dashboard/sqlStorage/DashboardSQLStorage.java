package io.supertokens.pluginInterface.dashboard.sqlStorage;

import io.supertokens.pluginInterface.dashboard.DashboardStorage;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.dashboard.exceptions.UserIdNotFoundException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.sqlStorage.SQLStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface DashboardSQLStorage extends DashboardStorage, SQLStorage {

    void updateDashboardUsersEmailWithUserId_Transaction(TransactionConnection con, String userId, String newEmail) throws StorageQueryException, DuplicateEmailException, UserIdNotFoundException;

    void updateDashboardUsersPasswordWithUserId_Transaction(TransactionConnection con, String userId, String newPassword) throws StorageQueryException, UserIdNotFoundException;
    
}