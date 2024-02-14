package org.winternote.winternote.metadata.exception;

import org.winternote.winternote.common.exception.WinterException;

public class UndeletableMetadataException extends WinterException {

    public UndeletableMetadataException(final String message) {
        super(message);
    }
}
