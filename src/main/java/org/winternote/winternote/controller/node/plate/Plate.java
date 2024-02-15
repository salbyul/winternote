package org.winternote.winternote.controller.node.plate;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.winternote.winternote.controller.node.element.Element;

import java.util.List;

public class Plate extends VBox {

    public Plate() {
        super();
        getChildren().add(new Element(this));
        setId("plate");
        this.setSpacing(5);
    }

    public void listen(final Element element, final CursorPosition position) { // 커서 처음과 마지막일 경우
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

    public void listenToSeparation(final Element element, final int from) { // 커서 중간에 있을 경우
        element.separate(from);
    }

    public List<String> getContents() {
        return getChildren().stream()
                .map(n -> ((Element) n).getValue())
                .toList();
    }
}