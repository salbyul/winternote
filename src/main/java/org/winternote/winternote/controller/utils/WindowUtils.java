package org.winternote.winternote.controller.utils;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.winternote.winternote.common.annotation.Utils;

@Utils
public final class WindowUtils {

    /**
     * Close all windows.
     */
    public void closeAllWindows() {
        ObservableList<Window> windows = Window.getWindows();
        while (!windows.isEmpty()) {
            ((Stage) windows.get(0)).close();
        }
    }
}
