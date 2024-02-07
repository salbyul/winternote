package org.winternote.winternote.note.domain;

import java.io.File;

import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

public class Note {

    private final String title;
    private final String path;
    private final String content;
    private static final String NOTE_EXTENSION = ".md";

    private Note(final String title, final String path, final String content) {
        this.title = title;
        this.path = path;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public File transferToFile() {
        return new File(getPath() + DELIMITER + title + NOTE_EXTENSION);
    }
    public static class Builder {
        private String title;
        private String path;
        private String content;

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder path(final String path) {
            this.path = path;
            return this;
        }

        public Builder content(final String content) {
            this.content = content;
            return this;
        }

        public Note build() {
            return new Note(title, path, content);
        }
    }
}
