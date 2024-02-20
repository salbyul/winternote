package org.winternote.winternote.note.syntax;

import java.util.stream.Stream;

public enum MarkdownSyntax {

    PLAIN {
        @Override
        public boolean correspondWith(final String line) {
            return false;
        }
    },
    HEADING1 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#(?!#)(.*)");
        }
    },
    HEADING2 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{2}(?!#)(.*)");
        }
    },
    HEADING3 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{3}(?!#)(.*)");
        }
    },
    HEADING4 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{4}(?!#)(.*)");
        }
    },
    HEADING5 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{5}(?!#)(.*)");
        }
    },
    HEADING6 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{6}(?!#)(.*)");
        }
    },
    BLOCKQUOTE {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(^> ?.+?)((\\r?\\n\\r?\\n\\w)|\\Z)");
        }
    },
    ORDERED_LIST {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("\\s*\\d.\\s.*");
        }
    },
    UNORDERED_LIST {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("\\s*-\\s.*");
        }
    };

    /**
     * Returns whether 'line' corresponds to a specific Markdown syntax.
     *
     * @param line Line.
     * @return Returns true if 'line' corresponds to a specific Markdown syntax, false otherwise.
     */
    public abstract boolean correspondWith(String line);

    /**
     * Returns the Markdown syntax equivalent to 'line'.
     *
     * @param line Line.
     * @return MarkdownSyntax, matching the Markdown syntax.
     */
    public static MarkdownSyntax getSyntaxFromString(final String line) {
        return Stream.of(values())
                .filter(syntax -> syntax.correspondWith(line))
                .findFirst()
                .orElse(PLAIN);
    }

    /**
     * Returns a MarkdownContent from the line.
     *
     * @param line Line.
     * @return MarkdownContent.
     */
    public static MarkdownContent getContentFromString(final String line) {
        MarkdownSyntax syntax = getSyntaxFromString(line);
        return new MarkdownContent(syntax, line);
    }
}
