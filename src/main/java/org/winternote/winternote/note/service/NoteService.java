package org.winternote.winternote.note.service;

import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.exception.NoteCreationException;
import org.winternote.winternote.note.repository.NoteRepository;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.common.service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

public class NoteService implements Service {

    private final NoteRepository noteRepository;
    private final WinterLogger logger;

    public NoteService(final NoteRepository noteRepository, final WinterLogger logger) {
        this.noteRepository = noteRepository;
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
        validateNoteName(noteName);
        Note note = Note.builder()
                .name(noteName)
                .path(project.getPath() + DELIMITER + project.getName() + DELIMITER + noteName)
                .lines(new ArrayList<>())
                .build();
        noteRepository.saveNewNote(note);
        logger.logNewNote(noteName, project.getName());
        return note;
    }

    private void validateNoteName(final String noteName) {
        if (Objects.isNull(noteName) || noteName.isEmpty() || noteName.isBlank()) {
            throw new NoteCreationException("The note name is empty.");
        }
    }
}
