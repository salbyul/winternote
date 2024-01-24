package org.winternote.winternote.controller.node.plate;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.winternote.winternote.controller.node.text.TextFootprint;

public class Plate extends VBox {

    public Plate() {
        super();
        getChildren().add(new TextFootprint(this));
        this.setSpacing(5);
    }

    public void listen(final TextFootprint node, final PlateCode plateCode, final Position position) {
        if (plateCode.isText()) {
            listenFromText(node, position);
        }
    }

    private void listenFromText(final TextFootprint node, final Position position) {
        ObservableList<Node> children = getChildren();
        if (position.isForward()) {
            TextFootprint textFootprint = new TextFootprint(this);
            children.add(textFootprint);
            textFootprint.requestFocus();
        } else if (position.isBackward()) {
            int index = children.indexOf(node);
            children.add(index, new TextFootprint(this));
        } else if (position.isMiddle()) {
            int caretPosition = node.getCaretPosition();
            int index = children.indexOf(node);
            String text = node.getText();
            TextFootprint leftTextFootprint = new TextFootprint(this, text.substring(0, caretPosition));
            TextFootprint rightTextFootprint = new TextFootprint(this, text.substring(caretPosition));

            children.set(index, rightTextFootprint);
            children.add(index, leftTextFootprint);
            rightTextFootprint.requestFocus();
        }
    }
}