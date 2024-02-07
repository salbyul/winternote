package org.winternote.winternote.model.property;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.winternote.winternote.model.exception.UnsupportedOSException;

public final class PrivateProperty {

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
        APPLICATION_PATH = generateApplicationPath();
    }

    private PrivateProperty() {}

    private static String generateApplicationPath() {
        if (isMac()) {
            return "/Users/" + USER_NAME + "/winternote";
        }
        throw new UnsupportedOSException("Not supported OS: " + OS);
    }

    /**
     * It checks user's platform is Mac OS.
     *
     * @return Whether user's platform is Mac OS.
     */
    public static boolean isMac() {
        return OS.startsWith("Mac");
    }
}
