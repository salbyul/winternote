package org.winternote.winternote.controller.utils;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowUtils {

    private WindowUtils() {}

    public static void closeAllWindows() {
        ObservableList<Window> windows = Window.getWindows();
        while (!windows.isEmpty()) {
            ((Stage)windows.get(0)).close();
        }
    }
}
