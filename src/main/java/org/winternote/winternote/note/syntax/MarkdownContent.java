package org.winternote.winternote.note.syntax;

public class MarkdownContent {

    private final MarkdownSyntax syntax;
    private final String value;

    public MarkdownContent(final MarkdownSyntax syntax, final String value) {
        this.syntax = syntax;
        this.value = value;
    }

    public MarkdownSyntax getSyntax() {
        return syntax;
    }

    public String getValue() {
        return value;
    }
}
