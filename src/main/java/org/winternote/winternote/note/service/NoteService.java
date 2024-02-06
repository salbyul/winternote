package org.winternote.winternote.note.service;

import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.common.service.Service;

import java.io.File;
import java.io.IOException;

public class NoteService implements Service {

    private static final NoteService instance = new NoteService();

    private final WinterLogger logger = WinterLogger.instance();

    private NoteService() {
    }

    public static NoteService instance() {
        return instance;
    }

    /**
     * Create a new note.
     *
     * @param project  Project that will contain new note.
     * @param noteName Note name.
     * @throws IOException Exception that may occur during the note creation process.
     */
    public void createNote(final Project project, final String noteName) throws IOException {
        File file = new File(project.getPath() + "/" + project.getName() + "/" + noteName + ".md");
        file.createNewFile();
        logger.logNewNote(noteName, project.getName());
    }
}
