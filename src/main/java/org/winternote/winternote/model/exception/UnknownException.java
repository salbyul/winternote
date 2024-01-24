package org.winternote.winternote.model.exception;

public class UnknownException extends RuntimeException {

    public UnknownException(final String message) {
        super(message);
    }

    public UnknownException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
