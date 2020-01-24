package io.supertokens.pluginInterface.exceptions;

public class QuitProgramFromPluginException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QuitProgramFromPluginException(String msg) {
        super(msg);
    }

    public QuitProgramFromPluginException(Exception e) {
        super(e);
    }
}
