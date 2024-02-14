package org.winternote.winternote.logging.exception;

public class LoggingException extends RuntimeException {

    public LoggingException(final String message) {
        super(message);
    }

    public LoggingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LoggingException(final Throwable cause) {
        super(cause);
    }
}
