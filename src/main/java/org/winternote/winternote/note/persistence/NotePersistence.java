package org.winternote.winternote.note.persistence;

import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.note.domain.Line;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;
import org.winternote.winternote.note.exception.DuplicatedNoteNameException;
import org.winternote.winternote.note.exception.NoteCreationException;
import org.winternote.winternote.note.exception.NoteNotFoundException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Persistence
public class NotePersistence {

    private final WinterLogger logger;

    public NotePersistence(final WinterLogger logger) {
        this.logger = logger;
    }

    /**
     * Make a new note.
     *
     * @param noteSummary The note will be made.
     * @throws IOException If an I/O error occurred
     */
    public void makeNote(final NoteSummary noteSummary) throws IOException {
        File file = noteSummary.transferToFile();
        if (file.exists())
            throw new DuplicatedNoteNameException();
        if (!file.createNewFile()) {
            throw new NoteCreationException();
        }
    }

    /**
     * Return specific note.
     *
     * @param note Note you want to read.
     * @return Note.
     */
    public List<Line> getNoteLines(final Note note) {
        List<Line> lines = new ArrayList<>();
        try {
            BufferedReader reader = generateReader(note);
            reader.lines().forEach(line -> lines.add(new Line(line)));
            reader.close();
            return lines;
        } catch (IOException e) {
            logger.logException(e);
            return lines;
        }
    }

    private BufferedReader generateReader(final Note note) {
        try {
            return new BufferedReader(new FileReader(note.getPath()));
        } catch (IOException e) {
            throw new NoteNotFoundException(e);
        }
    }
}
