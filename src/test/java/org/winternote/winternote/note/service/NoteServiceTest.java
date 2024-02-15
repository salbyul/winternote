package org.winternote.winternote.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;
import org.winternote.winternote.note.persistence.NotePersistence;

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
        String noteName = "noteName";
        String path = "path";

        // when
        doNothing().when(notePersistence).makeNote(any(NoteSummary.class));
        doNothing().when(winterLogger).logNewNote(noteName, path);
        Note note = noteService.createNote(noteName, path);

        // then
        verify(notePersistence, times(1)).makeNote(any(NoteSummary.class));
        verify(winterLogger, times(1)).logNewNote(noteName, path);
        assertThat(note.getName()).isEqualTo(noteName);
        assertThat(note.getLocation()).isEqualTo(path);
    }
}