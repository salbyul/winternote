package org.winternote.winternote.model.exception;

public class UnSupportedOSException extends RuntimeException {

    public UnSupportedOSException(final String message) {
        super(message);
    }

    public UnSupportedOSException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
