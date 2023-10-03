package io.supertokens.pluginInterface.mfa;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.sqlStorage.TransactionConnection;

public interface MfaStorage extends NonAuthRecipeStorage {
    // Enable (insert) a factor for a user and return true if it actually inserted something.
    boolean enableFactor(TenantIdentifier tenantIdentifier, String userId, String factorId) throws StorageQueryException;

    // List all the factors for a user:
    String[] listFactors(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    // Disable (delete) a factor for a user and return true if it actually deleted something.
    boolean disableFactor(TenantIdentifier tenantIdentifier, String userId, String factorId) throws StorageQueryException;

    // Delete a user from a tenant (with all the relevant factors)
    boolean deleteMfaInfoForUser(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;
}
