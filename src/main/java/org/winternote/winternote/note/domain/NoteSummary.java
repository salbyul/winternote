package org.winternote.winternote.note.domain;

import java.io.File;

import static org.winternote.winternote.application.property.PublicProperty.DELIMITER;
import static org.winternote.winternote.note.domain.Note.NOTE_EXTENSION;

public class NoteSummary {

    private final String name;
    private final String location;

    public NoteSummary(final String name, final String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public File transferToFile() {
        return new File(getLocation() + DELIMITER + getName() + NOTE_EXTENSION);
    }

    @Override
    public String toString() {
        return "NoteSummary{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
