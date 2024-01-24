package org.winternote.winternote.controller.node.text;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import org.winternote.winternote.controller.node.plate.Plate;
import org.winternote.winternote.controller.node.plate.PlateCode;
import org.winternote.winternote.controller.node.plate.Position;

import static org.winternote.winternote.controller.node.text.TextShortCut.*;

public class TextFootprint extends TextField {

    private final Plate plate;
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 40;

    public TextFootprint(final Plate plate) {
        super();
        this.plate = plate;
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

    public TextFootprint(final Plate plate, final String text) {
        this(plate);
        textProperty().set(text);
    }

    private void signal() {
        int caretPosition = getCaretPosition();
        int length = getLength();
        if (caretPosition == length) {
            plate.listen(this, PlateCode.TEXT, Position.FORWARD);
        } else if (caretPosition != 0 && caretPosition < length) {
            plate.listen(this, PlateCode.TEXT, Position.MIDDLE);
        } else if (caretPosition == 0) {
            plate.listen(this, PlateCode.TEXT, Position.BACKWARD);
        }
    }
}