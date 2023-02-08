package io.supertokens.pluginInterface.dashboard;

import io.supertokens.pluginInterface.Storage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateEmailException;
import io.supertokens.pluginInterface.dashboard.exceptions.DuplicateUserIdException;
import io.supertokens.pluginInterface.dashboard.exceptions.UserIdNotFoundException;

public interface DashboardStorage extends Storage {

    void createNewDashboardUser(DashboardUser userInfo) throws StorageQueryException, DuplicateUserIdException, DuplicateEmailException;

    DashboardUser getDashboardUserByEmail(String email) throws StorageQueryException;

    DashboardUser[] getAllDashboardUsers() throws StorageQueryException;

    boolean deleteDashboardUserWithEmail(String email) throws StorageQueryException;

    boolean deleteDashboardUserWithUserId(String userId) throws StorageQueryException;
    
    void createNewDashboardUserSession(String userId, String sessionId, long timeCreated, long expiry) throws StorageQueryException, UserIdNotFoundException;

    DashboardSessionInfo[] getAllSessionsForUserId(String userId) throws StorageQueryException;

    DashboardSessionInfo getSessionInfoWithSessionId(String sessionId) throws StorageQueryException;

    void revokeSessionWithSessionId(String sessionId) throws StorageQueryException;

    void revokeExpiredSessions(long expiry) throws StorageQueryException;
}
