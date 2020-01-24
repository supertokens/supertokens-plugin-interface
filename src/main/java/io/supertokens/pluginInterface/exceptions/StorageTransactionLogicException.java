package io.supertokens.pluginInterface.exceptions;

public class StorageTransactionLogicException extends Exception {

    private static final long serialVersionUID = 1L;

    public final Exception actualException;

    public StorageTransactionLogicException(Exception e) {
        super(e);
        this.actualException = e;
    }

}
