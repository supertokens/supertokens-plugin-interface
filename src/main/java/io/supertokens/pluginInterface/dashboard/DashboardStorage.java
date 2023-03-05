package io.supertokens.pluginInterface.dashboard;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.dashboard.exceptions.UserIdNotFoundException;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;

public interface DashboardStorage extends Storage {

    void createNewDashboardUser(AppIdentifier appIdentifier, DashboardUser userInfo)
            throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    DashboardUser getDashboardUserByEmail(AppIdentifier appIdentifier, String email) throws StorageQueryException;

    DashboardUser getDashboardUserByUserId(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    DashboardUser[] getAllDashboardUsers(AppIdentifier appIdentifier) throws StorageQueryException;

    boolean deleteDashboardUserWithUserId(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    void createNewDashboardUserSession(AppIdentifier appIdentifier, String userId, String sessionId, long timeCreated,
                                       long expiry) throws StorageQueryException, UserIdNotFoundException;

    DashboardSessionInfo[] getAllSessionsForUserId(AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    DashboardSessionInfo getSessionInfoWithSessionId(AppIdentifier appIdentifier, String sessionId)
            throws StorageQueryException;

    boolean revokeSessionWithSessionId(AppIdentifier appIdentifier, String sessionId) throws StorageQueryException;

    // this function removes based on expired time, so we can use this to globally remove from a particular db.
    void revokeExpiredSessions() throws StorageQueryException;
}
