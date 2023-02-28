package io.supertokens.pluginInterface.totp;

import javax.annotation.Nullable;

import io.supertokens.pluginInterface.authRecipe.AuthRecipeStorage;
import io.supertokens.pluginInterface.exceptions.StorageQueryException;
import io.supertokens.pluginInterface.totp.exception.DeviceAlreadyExistsException;
import io.supertokens.pluginInterface.totp.exception.TotpNotEnabledException;
import io.supertokens.pluginInterface.totp.exception.UnknownDeviceException;

public interface TOTPStorage extends AuthRecipeStorage {
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

        /** Insert a used TOTP code for an existing user: */
        void insertUsedCode(TOTPUsedCode code)
                        throws StorageQueryException, TotpNotEnabledException;

        /**
         * Get totp used codes for user (expired/non-expired) yet (sorted by descending
         * order of created time):
         */
        TOTPUsedCode[] getAllUsedCodes(String userId)
                        throws StorageQueryException;

        /** Remove expired codes from totp used codes for all users: */
        int removeExpiredCodes()
                        throws StorageQueryException;

        /** Delete all totp data associated with the user: */
        void deleteAllTotpDataForUser(String userId)
                        throws StorageQueryException;
}
