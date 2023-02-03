package io.supertokens.pluginInterface.dashboard;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateUserIdException;


public interface DashboardStorage extends Storage {

    void createNewDashboardUser(DashboardUser userInfo) throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    DashboardUser getDashboardUserByEmail(String email) throws StorageQueryException;

    DashboardUser[] getAllDashboardUsers() throws StorageQueryException;

    boolean deleteDashboardUserWithEmail(String email) throws StorageQueryException;

    boolean deleteDashboardUserWithUserId(String userId) throws StorageQueryException;
    
    void updateDashboardUserWithEmail(String email, String newEmail, String newPassword) throws StorageQueryException, DuplicateEmailException;

    void updateDashboardUserWithUserId(String userId, String newEmail, String newPassword) throws StorageQueryException, DuplicateEmailException;
}
