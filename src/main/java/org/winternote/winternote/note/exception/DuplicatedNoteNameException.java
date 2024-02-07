package org.winternote.winternote.note.exception;

import org.winternote.winternote.common.exception.WinterException;

public class DuplicatedNoteNameException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Duplicated note name.";

    public DuplicatedNoteNameException() {
        this(DEFAULT_MESSAGE);
    }

    public DuplicatedNoteNameException(final String message) {
        super(message);
    }
}
