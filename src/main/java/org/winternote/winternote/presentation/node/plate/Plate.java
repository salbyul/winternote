package org.winternote.winternote.presentation.node.plate;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.winternote.winternote.presentation.node.element.Element;

import java.util.List;

import static org.winternote.winternote.presentation.Shortcut.SIZE_DOWN_SHORTCUT;
import static org.winternote.winternote.presentation.Shortcut.SIZE_UP_SHORTCUT;

public class Plate extends VBox {

    private static final int DEFAULT_SPACING = 5;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 40;
    private double displaySize = 12;

    public Plate() {
        super();
        setId("plate");
        this.setSpacing(DEFAULT_SPACING);
        addSizeHandler();
    }

    /**
     * Adds an event handler for font size.
     */
    private void addSizeHandler() {
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (SIZE_UP_SHORTCUT.match(event) && displaySize < MAX_SIZE) {
                displaySize += 1;
                setFont(Font.font(displaySize));
            } else if (SIZE_DOWN_SHORTCUT.match(event) && displaySize > MIN_SIZE) {
                displaySize -= 1;
                setFont(Font.font(displaySize));
            }
        });
    }

    /**
     * Adds a new element based on the caret position.
     *
     * @param element  Element that sent a signal.
     * @param position The caret position.
     */
    public void listen(final Element element, final CursorPosition position) {
        ObservableList<Node> children = getChildren();
        int index = children.indexOf(element);
        Element newElement = new Element(this);

        if (position.isFirst()) {
            children.add(index, newElement);
        } else if (position.isLast()) {
            children.add(index + 1, newElement);
            newElement.positionCaretAt(0);
        }
    }

    /**
     * Returns an unmodifiable list of the elements contents.
     *
     * @return Unmodifiable list of the contents.
     */
    public List<String> getContents() {
        return getChildren().stream()
                .map(n -> ((Element) n).getText())
                .toList();
    }

    /**
     * Separates the element to two from current caret.
     * Then add two separated elements into the list of elements.
     * The existing element will be replaced separated.
     * Focus the second element of separated elements and position the caret at 0 of the second element.
     *
     * @param element The element to be separated.
     */
    public void listenToSeparation(final Element element) {
        int caretPosition = element.getCaretPosition();
        Element[] separated = element.separateAt(caretPosition);

        ObservableList<Node> children = getChildren();
        int index = children.indexOf(element);
        children.set(index, separated[0]);
        children.add(index + 1, separated[1]);
        separated[1].positionCaretAt(0);
    }

    /**
     * Replace these lines.
     *
     * @param lines Lines to be replaced.
     */
    public void replaceLines(final List<String> lines) {
        getChildren().clear();
        List<Element> list = lines.stream()
                .map(line -> new Element(this, line))
                .toList();
        getChildren().addAll(list);
    }

    /**
     * The font of elements is set to the font.
     *
     * @param font Font to be set.
     */
    private void setFont(final Font font) {
        ObservableList<Node> children = getChildren();
        children.forEach(node -> ((Element) node).setFont(font));
    }
}