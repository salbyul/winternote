package org.winternote.winternote.metadata.persistence;

public enum MetadataElement {
    LOCATION("Location"), RECENT_NOTES("recent notes");

    private final String value;

    MetadataElement(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
