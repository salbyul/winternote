package org.winternote.winternote.project.exception;

import org.winternote.winternote.common.exception.WinterException;

public class ProjectEmptyTitleException extends WinterException {

    private static final String DEFAULT_MESSAGE = "Title can not be empty";

    public ProjectEmptyTitleException() {
        this(DEFAULT_MESSAGE);
    }

    public ProjectEmptyTitleException(final String message) {
        super(message);
    }
}
