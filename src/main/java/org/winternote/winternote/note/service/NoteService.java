package org.winternote.winternote.note.service;

import org.springframework.stereotype.Service;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;
import org.winternote.winternote.note.persistence.NotePersistence;

import java.io.IOException;

@Service
public class NoteService {

    private final NotePersistence notePersistence;
    private final WinterLogger logger;

    public NoteService(final NotePersistence notePersistence, final WinterLogger logger) {
        this.notePersistence = notePersistence;
        this.logger = logger;
    }

    /**
     * Create a new note.
     *
     * @param noteName Note name.
     * @param path     Path the new note will be created.
     * @return Created note.
     * @throws IOException Exception that may occur during the note creation process.
     */
    public Note createNote(final String noteName, final String path) throws IOException {
        NoteSummary noteSummary = new NoteSummary(noteName, path);
        notePersistence.makeNote(noteSummary);
        logger.logNewNote(noteName, path);
        return Note.builder()
                .name(noteSummary.getName())
                .path(noteSummary.getPath())
                .build();
    }
}
