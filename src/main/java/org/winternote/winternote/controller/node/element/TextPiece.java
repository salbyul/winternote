package org.winternote.winternote.controller.node.element;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import org.winternote.winternote.controller.node.plate.PlateCode;
import org.winternote.winternote.controller.node.plate.CursorPosition;

import static org.winternote.winternote.controller.node.element.TextShortCut.SIZE_DOWN;
import static org.winternote.winternote.controller.node.element.TextShortCut.SIZE_UP;

public class TextPiece extends TextField {

    private final Element element;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 40;

    public TextPiece(final Element element) {
        this.element = element;
        setFont(Font.font(20));
        onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                signal();
            }
        });
        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            double size = getFont().getSize();
            if (SIZE_UP.match(event) && size < MAX_SIZE) {
                setFont(Font.font(size + 3));
            } else if (SIZE_DOWN.match(event) && size > MIN_SIZE) {
                setFont(Font.font(size - 3));
            }
        });
    }

    public TextPiece(final Element element, final String text) {
        this(element);
        textProperty().set(text);
    }

    private void signal() {
        int caretPosition = getCaretPosition();
        int length = getLength();
        if (caretPosition == length) { // 커서가 마지막에 있는 경우 - 새로운 엘리먼트 생성하는지 판단해야 됨
            element.listen(CursorPosition.LAST, PlateCode.TEXT);
        } else if (caretPosition == 0) { // 커서가 처음에 있는 경우 - 무조건 위에 새로운 줄 추가
            element.listen(CursorPosition.FIRST, PlateCode.TEXT);
        } else { // 커서가 중간에 있는 경우 - 무조건 쪼개기
            element.listenToSeparation(this);
        }
    }

    public void separate(final int index) {
        String text = getText();
        setText(text.substring(0, index));

        TextPiece textPiece = new TextPiece(element, text.substring(index));
        ObservableList<Node> children = element.getChildren();
        int indexOfThis = children.indexOf(this);
        children.add(indexOfThis + 1, textPiece);
    }
}
