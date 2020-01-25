package io.supertokens.pluginInterface;

import com.google.gson.JsonObject;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.tokenInfo.PastTokenInfo;

public interface Storage {

    // if silent is true, do not log anything out on the console
    void constructor(String processId, boolean silent);

    void loadConfig(String configFilePath);

    void initFileLogging(String infoLogPath, String errorLogPath);

    void stopLogging();

    // load tables and create connection pools
    void initStorage();

    // no need for transaction like feature here.
    // get stored appId. If nothing is stored, return null
    String getAppId() throws StorageQueryException;

    // no need for transaction like feature here.
    void setAppId(String appId) throws StorageQueryException;

    // no need for transaction like feature here.
    // get stored appId. If nothing is stored, return null
    String getUserDevProductionMode() throws StorageQueryException;

    // no need for transaction like feature here.
    void setUserDevProductionMode(String mode) throws StorageQueryException;

    // used by the core to do transactions the right way.
    STORAGE_TYPE getType();

    // to be used for testing purposes only.
    void deleteAllInformation() throws StorageQueryException;

    void close();

    // returns data from past_tokens table. If not found, returns null
    PastTokenInfo getPastTokenInfo(String refreshTokenHash2) throws StorageQueryException;

    void insertPastToken(PastTokenInfo info) throws StorageQueryException;

    // return number of rows else throw UnsupportedOperationException
    int getNumberOfPastTokens() throws StorageQueryException;

    void createNewSession(String sessionHandle, String userId, String refreshTokenHash2,
                          JsonObject userDataInDatabase, long expiry, JsonObject userDataInJWT, long createdAtTime)
            throws StorageQueryException;

    // return number of rows else throw UnsupportedOperationException
    int getNumberOfSessions() throws StorageQueryException;

    int deleteSession(String[] sessionHandles) throws StorageQueryException;

    String[] getAllSessionHandlesForUser(String userId) throws StorageQueryException;

    JsonObject getSessionData(String sessionHandle) throws StorageQueryException;

    int updateSessionData(String sessionHandle, JsonObject updatedData) throws StorageQueryException;

    void deleteAllExpiredSessions() throws StorageQueryException;

    void deletePastOrphanedTokens(long createdBefore) throws StorageQueryException;

    KeyValueInfo getKeyValue(String key) throws StorageQueryException;

    void setKeyValue(String key, KeyValueInfo info) throws StorageQueryException;

    void setStorageLayerEnabled(boolean enabled);
}
