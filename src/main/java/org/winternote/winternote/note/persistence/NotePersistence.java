package org.winternote.winternote.note.persistence;

import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.exception.DuplicatedNoteNameException;
import org.winternote.winternote.note.exception.NoteCreationException;

import java.io.File;
import java.io.IOException;

@Persistence
public class NotePersistence {

    public void makeNote(final Note note) throws IOException {
        File file = note.transferToFile();
        if (file.exists())
            throw new DuplicatedNoteNameException();
        if (!file.createNewFile()) {
            throw new NoteCreationException();
        }
    }
}
