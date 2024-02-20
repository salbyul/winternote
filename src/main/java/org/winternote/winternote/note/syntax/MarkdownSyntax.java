package org.winternote.winternote.note.syntax;

import java.util.stream.Stream;

public enum MarkdownSyntax {

    PLAIN {
        @Override
        public boolean correspondWith(final String line) {
            return false;
        }

        @Override
        public String extractContent(final String line) {
            return line;
        }
    },
    HEADING1 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(2);
        }
    },
    HEADING2 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{2}(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(3);
        }
    },
    HEADING3 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{3}(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(4);
        }
    },
    HEADING4 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{4}(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(5);
        }
    },
    HEADING5 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{5}(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(6);
        }
    },
    HEADING6 {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(?m)^#{6}(?!#)(.*)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(7);
        }
    },
    BLOCKQUOTE {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("(^> ?.+?)((\\r?\\n\\r?\\n\\w)|\\Z)");
        }

        @Override
        public String extractContent(final String line) {
            return line.substring(2);
        }
    },
    ORDERED_LIST {
        @Override
        public boolean correspondWith(final String line) {
            return line.matches("\\s*\\d.*");
        }

        @Override
        public String extractContent(final String line) {
            return line;
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
     * Extracts the actual value excluding Markdown syntax.
     *
     * @param line Line.
     * @return The actual value.
     */
    public abstract String extractContent(String line);

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
        String value = syntax.extractContent(line);
        return new MarkdownContent(syntax, value);
    }
}
