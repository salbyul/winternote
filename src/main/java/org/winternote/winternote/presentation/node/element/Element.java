package org.winternote.winternote.presentation.node.element;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import org.winternote.winternote.presentation.node.plate.CursorPosition;
import org.winternote.winternote.presentation.node.plate.Plate;

public class Element extends TextField {

    private final Plate plate;

    public Element(final Plate plate) {
        this.plate = plate;

        setFont(Font.font(20));
        onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                signal();
            }
        });
    }

    public Element(final Plate plate, final String text) {
        this(plate);
        textProperty().set(text);
    }

    private void signal() { // TextPiece
        int caretPosition = getCaretPosition();
        int length = getLength();
        if (caretPosition == length) {
            plate.listen(this, CursorPosition.LAST);
        } else if (caretPosition == 0) {
            plate.listen(this, CursorPosition.FIRST);
        } else {
            plate.listenToSeparation(this);
        }
    }

    public Element[] separateAt(final int index) {
        String text = getText();
        setText(text.substring(0, index));
        Element[] separated = new Element[2];
        separated[0] = new Element(plate, text.substring(0, index));
        separated[1] = new Element(plate, text.substring(index));
        return separated;
    }

    public void positionCaretAt(final int index) {
        requestFocus();
        positionCaret(index);
    }
}