package org.winternote.winternote.note.process;

import org.winternote.winternote.common.annotation.Process;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.service.NoteService;

import java.io.IOException;

@Process
public class NoteCreationProcess {

    private final NoteService noteService;
    private final MetadataService metadataService;

    public NoteCreationProcess(final NoteService noteService, final MetadataService metadataService) {
        this.noteService = noteService;
        this.metadataService = metadataService;
    }

    public void createNewNote(final String title, final String path) throws IOException { // TODO need transaction
        Note note = noteService.createNote(title, path);
        metadataService.addRecentNote(note);
        metadataService.changeLocation(path);
    }
}
