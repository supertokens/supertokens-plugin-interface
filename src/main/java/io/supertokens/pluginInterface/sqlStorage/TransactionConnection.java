package io.supertokens.pluginInterface.sqlStorage;

// This class enables the core to pass around a connection without knowing about it
public class TransactionConnection {
    private Object connection;

    public TransactionConnection(Object connection) {
        this.connection = connection;
    }

    public Object getConnection() {
        return connection;
    }
}
