package org.winternote.winternote.note.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

public class Note {

    private final String title;
    private final String path;
    private final List<Line> lines;
    private static final String NOTE_EXTENSION = ".md";

    private Note(final String title, final String path, final List<Line> lines) {
        this.title = title;
        this.path = path;
        this.lines = new ArrayList<>(lines);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
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

    public File transferToFile() {
        return new File(getPath() + DELIMITER + title + NOTE_EXTENSION);
    }

    public static class Builder {
        private String title;
        private String path;
        private List<Line> lines;

        public Builder title(final String title) {
            this.title = title;
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
            return new Note(title, path, lines);
        }
    }
}
