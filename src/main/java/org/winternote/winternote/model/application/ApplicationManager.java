package org.winternote.winternote.model.application;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.winternote.winternote.model.application.initializer.Initializer;
import org.winternote.winternote.model.exception.UnsupportedOSException;
import org.winternote.winternote.model.metadata.Metadata;

public class ApplicationManager {

    public static final double DISPLAY_WIDTH;
    public static final double DISPLAY_HEIGHT;
    public static final String OS;
    public static final String USER_NAME;
    public static final String APPLICATION_PATH;
    private static ApplicationManager instance;
    private final Metadata metadata;

    static {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        DISPLAY_WIDTH = bounds.getWidth();
        DISPLAY_HEIGHT = bounds.getHeight();
        OS = System.getProperty("os.name");
        USER_NAME = System.getProperty("user.name");
        APPLICATION_PATH = generateApplicationPath();
    }

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

    /**
     * Creates ApplicationManager instance.
     *
     * @param initializer initializer
     * @return ApplicationManager instance.
     */
    public static ApplicationManager instance(final Initializer initializer) {
        if (instance == null) {
            instance = new ApplicationManager(initializer);
        }
        return instance;
    }

    private ApplicationManager(final Initializer initializer) {
        if (instance != null) { // prevent reflect
            throw new UnsupportedOperationException("ApplicationManager has already been initialized. If you want to get instance, you need to invoke instance method.");
        }

        initializer.initialize();
        metadata = initializer.getMetadata();
    }
}