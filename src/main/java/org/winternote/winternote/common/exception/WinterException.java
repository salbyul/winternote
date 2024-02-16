package org.winternote.winternote.common.exception;

public class WinterException extends RuntimeException {

    public WinterException(final String message) {
        super(message);
    }

    public WinterException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
