package org.winternote.winternote.note.exception;

import org.winternote.winternote.common.exception.WinterException;

public class NotNoteException extends WinterException {

    public NotNoteException(final String fileName) {
        super(String.format("%s is not a note.", fileName));
    }
}
