package org.winternote.winternote.project.exception;

import org.winternote.winternote.common.exception.WinterException;

public class ProjectCreationException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Failed to create new project.";

    public ProjectCreationException() {
        this(DEFAULT_MESSAGE);
    }

    public ProjectCreationException(final String message) {
        super(message);
    }
}
