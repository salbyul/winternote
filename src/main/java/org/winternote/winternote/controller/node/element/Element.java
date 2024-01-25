package org.winternote.winternote.controller.node.element;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import org.winternote.winternote.controller.node.plate.CursorPosition;
import org.winternote.winternote.controller.node.plate.Plate;
import org.winternote.winternote.controller.node.plate.PlateCode;

import java.util.ArrayList;
import java.util.List;

public class Element extends HBox {

    private final Plate plate;

    public Element(final Plate plate) {
        this.plate = plate;
        getChildren().add(new TextPiece(this));
    }

    public Element(final Plate plate, final List<TextPiece> children) {
        this.plate = plate;
        getChildren().clear();
        copyChildren(children);
    }

    private void copyChildren(final List<TextPiece> children) {
        for (TextPiece child : children) {
            TextPiece textPiece = new TextPiece(this, child.getText());
            getChildren().add(textPiece);
        }
    }

    public void listen(final CursorPosition position, final PlateCode code) { // 커서 처음과 마지막일 경우 - 마지막일 경우 새로운 엘리먼트 생성하는 지 확인
        if (code.isText()) {
            plate.listen(this, position);
        }
    }

    public void listenToSeparation(final TextPiece textPiece) { // 커서가 중간에 있는 경우
        int caretPosition = textPiece.getCaretPosition();
        textPiece.separate(caretPosition);

        ObservableList<Node> children = getChildren();
        int index = children.indexOf(textPiece);
        plate.listenToSeparation(this, index + 1);
    }

    public void positionCaretAtFirst() {
        TextPiece node = (TextPiece) getChildren().get(0);
        node.requestFocus();
        node.positionCaret(0);
    }

    public void separate(final int index) {
        List<TextPiece> list = new ArrayList<>();
        ObservableList<Node> children = getChildren();
        int size = children.size();
        for (int i = index; i < size; i++) {
            list.add((TextPiece) children.get(i)); // 강제 형변환 다 제거 불가능?
        }
        Element newElement = new Element(plate, list);
        while (index != children.size()) {
            children.remove(index);
        }
        int index1 = plate.getChildren().indexOf(this);
        plate.getChildren().add(index1 + 1, newElement);
        newElement.positionCaretAtFirst();
    }
}