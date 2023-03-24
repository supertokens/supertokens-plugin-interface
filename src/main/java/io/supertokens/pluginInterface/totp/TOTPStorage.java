package io.supertokens.pluginInterface.totp;

import io.supertokens.pluginInterface.nonAuthRecipe.NonAuthRecipeStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPStorage extends NonAuthRecipeStorage {
        /** Create a new device and a new user if the user does not exist: */
        void createDevice(TOTPDevice device)
                        throws StorageQueryException, DeviceAlreadyExistsException;

        /** Verify a user's device with the given name: */
        void markDeviceAsVerified(String userId, String deviceName)
                        throws StorageQueryException, UnknownDeviceException;

        /** Update device name of a device: */
        void updateDeviceName(String userId, String oldDeviceName, String newDeviceName)
                        throws StorageQueryException, DeviceAlreadyExistsException,
                        UnknownDeviceException;

        /** Get the devices for a user */
        TOTPDevice[] getDevices(String userId)
                        throws StorageQueryException;

        /** Remove expired codes from totp used codes for all users: */
        int removeExpiredCodes(long expiredBefore)
                        throws StorageQueryException;
}
