package io.supertokens.pluginInterface.totp;

import javax.annotation.Nullable;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.totp.TOTPDevice;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.InvalidCodeException;
import io.supertokens.pluginInterface.totp.exception.LimitReachedException;
import io.supertokens.pluginInterface.totp.exception.NotEnabledException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPStorage extends AuthRecipeStorage {
    // Create a new device:
    void createDevice(String userId, String deviceName, String secret, int skew, int period)
            throws StorageQueryException, DeviceAlreadyExistsException;

    // Verify the TOTP code:
    void verifyCode(String userId, String deviceName, String code)
            throws StorageQueryException, InvalidCodeException, NotEnabledException, LimitReachedException;

    // Verify the device with the given name:
    void verifyDevice(String userId, String deviceName)
            throws StorageQueryException, InvalidCodeException, NotEnabledException, UnknownDeviceException;

    // Delete the device with the given name:
    void deleteDevice(String userId, String deviceName) throws StorageQueryException, NotEnabledException;

    // update device name:
    void updateDeviceName(String userId, String oldDeviceName, String newDeviceName)
            throws StorageQueryException, NotEnabledException, DeviceAlreadyExistsException, UnknownDeviceException;

    // Get the devices
    TOTPDevice[] getDevices(String userId, @Nullable String deviceName)
            throws StorageQueryException, NotEnabledException;

    // Get all the device names for the given user:
    String[] getDeviceNames(String userId) throws StorageQueryException;

}
