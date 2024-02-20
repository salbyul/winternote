package org.winternote.winternote.presentation.node.drawingpaper;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.winternote.winternote.note.domain.Line;
import org.winternote.winternote.note.syntax.MarkdownContent;
import org.winternote.winternote.note.syntax.MarkdownSyntax;
import org.winternote.winternote.presentation.node.plate.Plate;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.winternote.winternote.note.syntax.MarkdownSyntax.*;

public class DrawingPaper extends VBox {

    private final Plate plate;

    public DrawingPaper(final Plate plate) {
        this.plate = plate;
        drawing();
    }

    /**
     * Take text from a plate and draw Markdown.
     */
    private void drawing() {
        List<Line> lines = plate.getContents().stream()
                .map(Line::new)
                .toList();
        ObservableList<Node> children = getChildren();
        children.clear();
        lines.forEach(line -> {
            MarkdownContent content = getContentFromString(line.getContent());
            children.add(
                    ElementGenerator.generate(
                            content.getSyntax(),
                            content.getValue()
                    )
            );
        });
    }

    private static class ElementGenerator {

        private static final Map<MarkdownSyntax, ElementGeneratedStrategy> nodeMap = new EnumMap<>(MarkdownSyntax.class);

        public static final int HEADING_ONE_SIZE = 80;
        public static final int HEADING_TWO_SIZE = 70;
        public static final int HEADING_THREE_SIZE = 60;
        public static final int HEADING_FOUR_SIZE = 50;
        public static final int HEADING_FIVE_SIZE = 40;
        public static final int HEADING_SIX_SIZE = 30;
        public static final int PLAIN_SIZE = 24;

        static {
            nodeMap.put(PLAIN, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(PLAIN_SIZE));
                return text;
            });
            nodeMap.put(HEADING1, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_ONE_SIZE));
                return text;
            });
            nodeMap.put(HEADING2, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_TWO_SIZE));
                return text;
            });
            nodeMap.put(HEADING3, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_THREE_SIZE));
                return text;
            });
            nodeMap.put(HEADING4, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_FOUR_SIZE));
                return text;
            });
            nodeMap.put(HEADING5, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_FIVE_SIZE));
                return text;
            });
            nodeMap.put(HEADING6, value -> {
                Text text = new Text(value);
                text.setFont(Font.font(HEADING_SIX_SIZE));
                return text;
            });
        }

        private static Node generate(final MarkdownSyntax syntax, final String value) {
            return nodeMap.get(syntax).generate(value);
        }
    }

    @FunctionalInterface
    private interface ElementGeneratedStrategy {

        Node generate(String value);
    }
}
