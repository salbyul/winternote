package org.winternote.winternote.note.domain;

import java.io.File;

import static org.winternote.winternote.application.property.PublicProperty.DELIMITER;
import static org.winternote.winternote.note.domain.Note.NOTE_EXTENSION;

public class NoteSummary {

    private final String name;
    private final String path;

    public NoteSummary(final String name, final String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public File transferToFile() {
        return new File(getPath() + DELIMITER + getName() + NOTE_EXTENSION);
    }

    @Override
    public String toString() {
        return "NoteSummary{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
