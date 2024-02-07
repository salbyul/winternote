package org.winternote.winternote.note.exception;

import org.winternote.winternote.common.exception.WinterException;

public class NoteCreationException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Failed to create new note.";

    public NoteCreationException() {
        this(DEFAULT_MESSAGE);
    }

    public NoteCreationException(final String message) {
        super(message);
    }
}
