package org.winternote.winternote.note.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.winternote.winternote.note.exception.NoteCreationException;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class NoteTest {

    @Test
    void noteBuild() {
        // given
        Note note = Note.builder()
                .title("title")
                .path("path")
                .lines(List.of())
                .build();

        // when

        // then
        assertThat(note.getTitle()).isEqualTo("title");
        assertThat(note.getPath()).isEqualTo("path");
        assertThat(note.getLines()).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If the title is empty or null, a NoteCreationException should be thrown.")
    void failNoteBuildSinceEmptyTitle(String input) {
        // given
        Note.Builder builder = Note.builder()
                .title(input)
                .path("path")
                .lines(List.of());

        // when

        // then
        assertThatThrownBy(builder::build)
                .isInstanceOf(NoteCreationException.class)
                .hasMessage("The note title is empty.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If the path is empty or null, a NoteCreationException should be thrown.")
    void failNoteBuildSinceEmptyPath(String input) {
        // given
        Note.Builder builder = Note.builder()
                .title("title")
                .path(input)
                .lines(List.of());

        // when

        // then
        assertThatThrownBy(builder::build)
                .isInstanceOf(NoteCreationException.class)
                .hasMessage("The note path is empty.");
    }

    @Test
    @DisplayName("The UnsupportedOperationException occurs when modifying the list.")
    void failModifyUnmodifiableLines() {
        // given
        Note note = Note.builder()
                .title("title")
                .path("path")
                .lines(List.of())
                .build();
        List<Line> unmodifiableLines = note.getUnmodifiableLines();
        Line testLine = new Line("test");

        // when

        // then
        assertThatThrownBy(() -> unmodifiableLines.add(testLine))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Note can be converted to file.")
    void convertToFile() {
        // given
        Note note = Note.builder()
                .title("title")
                .path("path")
                .lines(List.of())
                .build();

        // when
        File file = note.transferToFile();

        // then
        assertThat(file.getPath()).isEqualTo("path/title.md");
    }
}