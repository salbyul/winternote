package org.winternote.winternote.controller.node.plate;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.winternote.winternote.controller.node.element.Element;

public class Plate extends VBox {

    public Plate() {
        super();
        getChildren().add(new Element(this));
        this.setSpacing(5);
    }

    public void listen(final Element element, final CursorPosition position) {
        ObservableList<Node> children = getChildren();
        int index = children.indexOf(element);
        Element newElement = new Element(this);

        if (position.isFirst()) {
            children.add(index, newElement);
        } else if (position.isLast()) {
            children.add(index + 1, newElement);
            newElement.positionCaretAtFirst();
        }
    }

    public void listen(final Element element, final int from) {
        element.separate(from);
    }
}