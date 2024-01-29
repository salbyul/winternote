package org.winternote.winternote.model.exception;

public class InitialException extends RuntimeException {

    public InitialException(final String message) {
        super(message);
    }

    public InitialException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InitialException(final Throwable cause) {
        super(cause);
    }
}
