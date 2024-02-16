package org.winternote.winternote.note.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.note.domain.Line;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;
import org.winternote.winternote.note.persistence.NotePersistence;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    NotePersistence notePersistence;

    @Mock
    WinterLogger logger;

    @InjectMocks
    NoteService noteService;

    @Test
    void createNote() throws IOException {
        // given
        String noteName = "noteName";
        String path = "path";

        // when
        doNothing().when(notePersistence).makeNote(any(NoteSummary.class));
        doNothing().when(logger).logNewNote(noteName, path);
        Note note = noteService.createNote(noteName, path);

        // then
        verify(notePersistence, times(1)).makeNote(any(NoteSummary.class));
        verify(logger, times(1)).logNewNote(noteName, path);
        assertThat(note.getName()).isEqualTo(noteName);
        assertThat(note.getLocation()).isEqualTo(path);
    }

    @Test
    @DisplayName("Loads the contents of a specific note")
    void loadNoteLines() {
        // given
        Note note = Note.builder()
                .name("note")
                .location("location")
                .build();
        List<Line> lines = List.of(new Line("Line1"), new Line("Line2"));

        // when
        when(notePersistence.getNoteLines(note)).thenReturn(lines);
        noteService.loadNoteLines(note);

        // then
        verify(notePersistence, times(1)).getNoteLines(note);
        assertThat(note.getUnmodifiableLines()).hasSize(2);
        assertThat(note.getUnmodifiableLines()).containsExactlyInAnyOrder(new Line("Line1"), new Line("Line2"));
    }

    @Test
    @DisplayName("Save a note")
    void saveNote() {
        // given
        Note note = Note.builder()
                .name("note")
                .location("location")
                .build();

        // when
        doNothing().when(notePersistence).save(note);
        doNothing().when(logger).logSave(note.getName(), note.getLocation());
        noteService.saveNote(note);

        // then
        verify(notePersistence, times(1)).save(note);
        verify(logger, times(1)).logSave(note.getName(), note.getLocation());
    }
}