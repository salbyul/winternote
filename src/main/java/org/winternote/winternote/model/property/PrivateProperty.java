package org.winternote.winternote.model.property;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.winternote.winternote.model.exception.UnsupportedOSException;

public abstract class PrivateProperty {

    public static final double DISPLAY_WIDTH;
    public static final double DISPLAY_HEIGHT;
    public static final String OS;
    public static final String USER_NAME;
    public static final String APPLICATION_PATH;

    static {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        DISPLAY_WIDTH = bounds.getWidth();
        DISPLAY_HEIGHT = bounds.getHeight();
        OS = System.getProperty("os.name");
        USER_NAME = System.getProperty("user.name");

        if (isMac()) {
            APPLICATION_PATH = "/Users/" + USER_NAME + "/winternote";
        } else {
            throw new UnsupportedOSException("Not supported OS: " + OS);
        }
    }

    private PrivateProperty() {}

    public static boolean isMac() {
        return OS.startsWith("Mac");
    }

    public static boolean isSupportedOS() {
        return isMac();
    }
}