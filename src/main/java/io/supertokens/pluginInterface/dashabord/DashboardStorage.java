package io.supertokens.pluginInterface.dashabord;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.emailpassword.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;

public interface DashboardStorage extends Storage {

    void createNewDashboardUser(DashboardUser userInfo) throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    DashboardUser getDashboardUserByEmail(String email) throws StorageQueryException;

    DashboardUser[] getAllDashboardUsers() throws StorageQueryException;

    boolean deleteDashboardUserWithEmail(String email) throws StorageQueryException;

    boolean deleteDashboardUserWithUserId(String userId) throws StorageQueryException;   
}
