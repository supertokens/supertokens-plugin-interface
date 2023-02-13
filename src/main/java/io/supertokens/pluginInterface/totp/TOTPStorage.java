package io.supertokens.pluginInterface.totp;

import javax.annotation.Nullable;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.TotpNotEnabledException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPStorage extends AuthRecipeStorage {
        // Create a new device:
        void createDevice(TOTPDevice device)
                        throws StorageQueryException, DeviceAlreadyExistsException;

        // Verify the device with the given name:
        void markDeviceAsVerified(String userId, String deviceName)
                        throws StorageQueryException, UnknownDeviceException;

        // Delete the device with the given name:
        void deleteDevice(String userId, String deviceName)
                        throws StorageQueryException, UnknownDeviceException;

        // update device name:
        void updateDeviceName(String userId, String oldDeviceName, String newDeviceName)
                        throws StorageQueryException, DeviceAlreadyExistsException,
                        UnknownDeviceException;

        // Get the devices
        TOTPDevice[] getDevices(String userId)
                        throws StorageQueryException;

        // Insert a used TOTP code:
        boolean insertUsedCode(TOTPUsedCode code)
                        throws StorageQueryException, TotpNotEnabledException;

        // Get totp used code based on userId:
        TOTPUsedCode[] getUsedCodes(String userId)
                        throws StorageQueryException;

        // Remove expired codes from totp used codes:
        void removeExpiredCodes()
                        throws StorageQueryException;
}
