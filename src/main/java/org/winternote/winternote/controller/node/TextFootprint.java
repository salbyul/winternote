package org.winternote.winternote.controller.node;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.winternote.winternote.controller.node.plate.Plate;
import org.winternote.winternote.controller.node.plate.PlateCode;
import org.winternote.winternote.controller.node.plate.Position;

public class TextFootprint extends TextField {

    private final Plate plate;

    public TextFootprint(final Plate plate) {
        super();
        this.plate = plate;
        onKeyPressedProperty().set(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                signal();
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