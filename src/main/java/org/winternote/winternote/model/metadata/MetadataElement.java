package org.winternote.winternote.model.metadata;

public enum MetadataElement {
    LOCATION("Location"), RECENT_PROJECTS("recent projects");

    private final String value;

    MetadataElement(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
