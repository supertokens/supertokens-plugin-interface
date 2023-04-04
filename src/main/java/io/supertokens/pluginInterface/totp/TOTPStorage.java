package io.supertokens.pluginInterface.totp;

import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.multitenancy.AppIdentifier;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPStorage extends NonAuthRecipeStorage {
    /**
     * Create a new device and a new user if the user does not exist:
     */
    void createDevice(AppIdentifier appIdentifier, TOTPDevice device)
            throws StorageQueryException, DeviceAlreadyExistsException;

    /**
     * Verify a user's device with the given name:
     */
    void markDeviceAsVerified(AppIdentifier appIdentifier, String userId, String deviceName)
            throws StorageQueryException, UnknownDeviceException;

    /**
     * Update device name of a device:
     */
    void updateDeviceName(AppIdentifier appIdentifier, String userId, String oldDeviceName, String newDeviceName)
            throws StorageQueryException, DeviceAlreadyExistsException,
            UnknownDeviceException;

    /**
     * Get the devices for a user
     */
    TOTPDevice[] getDevices(AppIdentifier appIdentifier, String userId)
            throws StorageQueryException;

    /**
     * Remove expired codes from totp used codes for all users:
     */
    int removeExpiredCodes(TenantIdentifier tenantIdentifier, long expiredBefore)
            throws StorageQueryException;
}
