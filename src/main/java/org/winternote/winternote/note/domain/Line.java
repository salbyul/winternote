package org.winternote.winternote.note.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(content, line.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
