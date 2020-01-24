package io.supertokens.pluginInterface.exceptions;

public class StorageQueryException extends Exception {

    private static final long serialVersionUID = 1L;

    public StorageQueryException(Exception e) {
        super(e);
    }
}
