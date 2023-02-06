package io.supertokens.pluginInterface.totp.exception;

import io.supertokens.pluginInterface.emailpassword.exceptions.EmailPasswordException;

// Why extending EmailPasswordException everywhere ?
public class DeviceAlreadyExistsException extends EmailPasswordException {
    private static final long serialVersionUID = 6848053563771647272L; // How's this decided ?
}
