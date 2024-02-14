package org.winternote.winternote.note.service;

import org.springframework.stereotype.Service;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.persistence.NotePersistence;
import org.winternote.winternote.project.domain.Project;

import java.io.IOException;
import java.util.ArrayList;

import static org.winternote.winternote.application.property.PublicProperty.DELIMITER;

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
     * @param project  Project that will contain new note.
     * @param noteName Note name.
     * @throws IOException Exception that may occur during the note creation process.
     */
    public Note createNote(final Project project, final String noteName) throws IOException {
        Note note = Note.builder()
                .name(noteName)
                .path(project.getPath() + DELIMITER + noteName)
                .lines(new ArrayList<>())
                .build();
        notePersistence.makeNote(note);
        logger.logNewNote(noteName, project.getName());
        return note;
    }
}
