package org.winternote.winternote.controller.utils.message;

public enum Message {

    CONFIGURATION_ERROR("설정 파일에 문제가 있습니다."),

    TITLE_EMPTY_ERROR("Title cannot be empty");

    private final String content;

    Message(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
