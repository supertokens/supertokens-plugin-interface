package io.supertokens.pluginInterface.mfa;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;

public interface MfaStorage extends NonAuthRecipeStorage {
    // Enable (insert) a factor for a user and return true if it actually inserted something.
    boolean enableFactor(TenantIdentifier tenantIdentifier, String userId, String factorId) throws StorageQueryException;

    // List all the factors for a user:
    String[] listFactors(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;

    // Disable (delete) a factor for a user and return true if it actually deleted something.
    boolean disableFactor(TenantIdentifier tenantIdentifier, String userId, String factorId) throws StorageQueryException;

    // Delete a user across all tenants (with all the relevant factors)
    boolean deleteUser(AppIdentifier appIdentifier, String userId) throws StorageQueryException;

    // Delete a user from a tenant (with all the relevant factors)
    boolean deleteUserFromTenant(TenantIdentifier tenantIdentifier, String userId) throws StorageQueryException;
}
