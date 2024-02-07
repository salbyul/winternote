package org.winternote.winternote.note.repository;

import org.winternote.winternote.common.repository.Repository;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.exception.DuplicatedNoteNameException;
import org.winternote.winternote.note.exception.NoteCreationException;

import java.io.File;
import java.io.IOException;

public class NoteRepository implements Repository {

    public void saveNewNote(final Note note) throws IOException {
        File file = note.transferToFile();
        if (file.exists())
            throw new DuplicatedNoteNameException();
        if (!file.createNewFile()) {
            throw new NoteCreationException();
        }
    }
}
