package org.winternote.winternote.note.exception;

import org.winternote.winternote.common.exception.WinterException;

public class NoteNotFoundException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Can't find the note";

    public NoteNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NoteNotFoundException(final Exception e) {
        super(DEFAULT_MESSAGE, e);
    }

    public NoteNotFoundException(final String message, final Exception e) {
        super(message, e);
    }

    public NoteNotFoundException(final String message) {
        super(message);
    }
}
