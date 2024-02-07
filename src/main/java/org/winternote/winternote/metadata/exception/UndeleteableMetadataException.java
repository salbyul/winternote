package org.winternote.winternote.metadata.exception;

import org.winternote.winternote.common.exception.WinterException;

public class UndeleteableMetadataException extends WinterException {

    public UndeleteableMetadataException(final String message) {
        super(message);
    }
}
