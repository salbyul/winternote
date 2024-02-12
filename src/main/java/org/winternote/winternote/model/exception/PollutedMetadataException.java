package org.winternote.winternote.model.exception;

import org.winternote.winternote.metadata.persistence.MetadataElement;

public class PollutedMetadataException extends RuntimeException {

    private final MetadataElement element;

    public PollutedMetadataException(final String message, final MetadataElement element) {
        super(message);
        this.element = element;
    }

    public PollutedMetadataException(final String message, final Throwable cause, final MetadataElement element) {
        super(message, cause);
        this.element = element;
    }

    public MetadataElement getElement() {
        return element;
    }
}
