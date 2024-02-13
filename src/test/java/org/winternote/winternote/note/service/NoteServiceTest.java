package org.winternote.winternote.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.persistence.NotePersistence;
import org.winternote.winternote.project.domain.Project;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    NotePersistence notePersistence;

    @Mock
    WinterLogger winterLogger;

    @InjectMocks
    NoteService noteService;

    @Test
    void createNote() throws IOException {
        // given
        Project project = new Project("projectName", "path");
        String noteName = "noteName";

        // when
        doNothing().when(notePersistence).makeNote(any(Note.class));
        doNothing().when(winterLogger).logNewNote(noteName, project.getName());
        Note note = noteService.createNote(project, noteName);

        // then
        verify(notePersistence, times(1)).makeNote(any(Note.class));
        verify(winterLogger, times(1)).logNewNote(noteName, project.getName());
        assertThat(note.getName()).isEqualTo(noteName);
        assertThat(note.getPath()).isEqualTo(project.getPath() +  "/" + noteName);
        assertThat(note.getLines()).isEmpty();
    }
}