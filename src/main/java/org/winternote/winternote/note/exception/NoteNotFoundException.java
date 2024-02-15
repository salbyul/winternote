package org.winternote.winternote.note.exception;

import org.winternote.winternote.common.exception.WinterException;

public class NoteNotFoundException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Can't find the note.";

    public NoteNotFoundException(final Throwable throwable) {
        super(DEFAULT_MESSAGE, throwable);
    }

    public NoteNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NoteNotFoundException(final String message) {
        super(message);
    }

    public NoteNotFoundException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
