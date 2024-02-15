package org.winternote.winternote.note.domain;

import org.winternote.winternote.note.exception.NoteCreationException;

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
        if (Objects.isNull(name) || name.isEmpty() || name.isBlank()) {
            throw new NoteCreationException("The note title is empty.");
        }
        if (Objects.isNull(location) || location.isEmpty() || location.isBlank()) {
            throw new NoteCreationException("The note path is empty.");
        }
        this.path = location + DELIMITER + name + NOTE_EXTENSION;
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

    public List<Line> getLines() {
        return lines;
    }

    public List<Line> getUnmodifiableLines() {
        return Collections.unmodifiableList(lines);
    }

    public static Note of(final NoteSummary noteSummary) {
        return Note.builder()
                .name(noteSummary.getName())
                .path(noteSummary.getLocation())
                .build();
    }

    /**
     * Replace lines to new lines.
     *
     * @param lines New lines.
     */
    public void replaceLines(final List<Line> lines) {
        this.lines.clear();
        this.lines.addAll(lines);
    }

    public static class Builder {

        private String name;
        private String path;
        private List<Line> lines = new ArrayList<>();

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder path(final String path) {
            this.path = path;
            return this;
        }

        public Builder lines(final List<Line> lines) {
            this.lines = new ArrayList<>(lines);
            return this;
        }

        public Note build() {
            return new Note(name, path, lines);
        }
    }
}
