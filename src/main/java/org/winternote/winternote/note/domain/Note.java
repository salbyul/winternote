package org.winternote.winternote.note.domain;

import org.winternote.winternote.note.exception.NoteCreationException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.winternote.winternote.application.property.PublicProperty.DELIMITER;

public class Note {

    private final String name;
    private final String location;
    private final String path;
    private final List<Line> lines;
    public static final String NOTE_EXTENSION = ".md";

    private Note(final String name, final String location, final List<Line> lines) {
        this.name = name;
        this.location = location;
        this.lines = new ArrayList<>(lines);
        this.path = location + DELIMITER + name + NOTE_EXTENSION;
        if (Objects.isNull(name) || name.isEmpty() || name.isBlank()) {
            throw new NoteCreationException("The note title is empty.");
        }
        if (Objects.isNull(location) || location.isEmpty() || location.isBlank()) {
            throw new NoteCreationException("The note path is empty.");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPath() {
        return path;
    }

    public List<Line> getUnmodifiableLines() {
        return Collections.unmodifiableList(lines);
    }

    /**
     * Returns a Note from NoteSummary.
     *
     * @param noteSummary NoteSummary.
     * @return Note.
     */
    public static Note of(final NoteSummary noteSummary) {
        return Note.builder()
                .name(noteSummary.getName())
                .location(noteSummary.getLocation())
                .build();
    }

    /**
     * Returns a note from a file.
     *
     * @param file File.
     * @return Note.
     */
    public static Note of(final File file) {
        String name = file.getName().substring(0, file.getName().length() - NOTE_EXTENSION.length());
        String location = file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1);
        return Note.builder()
                .name(name)
                .location(location)
                .build();
    }

    /**
     * Replace these lines with new lines.
     *
     * @param lines New lines.
     */
    public void replaceLines(final List<Line> lines) {
        this.lines.clear();
        this.lines.addAll(lines);
    }

    public List<String> getLinesAsString() {
        return lines.stream()
                .map(Line::getContent)
                .toList();
    }

    public static class Builder {

        private String name;
        private String location;
        private List<Line> lines = new ArrayList<>();

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder location(final String location) {
            this.location = location;
            return this;
        }

        public Builder lines(final List<Line> lines) {
            this.lines = new ArrayList<>(lines);
            return this;
        }

        public Note build() {
            return new Note(name, location, lines);
        }
    }
}
