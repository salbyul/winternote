package org.winternote.winternote.note.domain;

public class Line {

    private String content;

    public Line(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void updateLine(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
