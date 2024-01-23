package org.winternote.winternote.controller.utils.message;

public enum Message {

    CONFIGURATION_ERROR("There is a problem with settings file."),
    UNKNOWN_ERROR("Unknown Error occurred."),

    TITLE_EMPTY_ERROR("Title cannot be empty.");

    private final String content;

    Message(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
