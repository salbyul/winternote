package org.winternote.winternote.model.application;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.winternote.winternote.model.application.initializer.Initializer;
import org.winternote.winternote.model.exception.UnsupportedOSException;
import org.winternote.winternote.model.metadata.Metadata;
import org.winternote.winternote.model.metadata.MetadataHandler;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.project.service.ProjectService;
import org.winternote.winternote.common.service.Service;

import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {

    public static final double DISPLAY_WIDTH;
    public static final double DISPLAY_HEIGHT;
    public static final String OS;
    public static final String USER_NAME;
    public static final String APPLICATION_PATH;
    private static ApplicationManager instance;
    private final Metadata metadata;
    private static final Map<Class<? extends Service>, Service> serviceMap;
    private static final MetadataHandler metadataHandler;

    static {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        DISPLAY_WIDTH = bounds.getWidth();
        DISPLAY_HEIGHT = bounds.getHeight();
        OS = System.getProperty("os.name");
        USER_NAME = System.getProperty("user.name");
        APPLICATION_PATH = generateApplicationPath();
        serviceMap = new HashMap<>();
        serviceMap.put(NoteService.class, NoteService.instance());
        serviceMap.put(ProjectService.class, ProjectService.instance());
        metadataHandler = MetadataHandler.instance();
    }

    public static MetadataHandler getMetadataHandler() {
        return metadataHandler;
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

    /**
     * Returns ApplicationManager instance.
     *
     * @return ApplicationManager instance.
     */
    public static ApplicationManager instance() {
        return instance;
    }

    private ApplicationManager(final Initializer initializer) {
        if (instance != null) { // prevent reflect
            throw new UnsupportedOperationException("ApplicationManager has already been initialized. If you want to get instance, you need to invoke instance method.");
        }

        initializer.initialize();
        metadata = initializer.getMetadata();
    }

    /**
     * Return recent location.
     *
     * @return Recent location.
     */
    public String getRecentLocation() {
        return metadata.getLocation();
    }

    /**
     * Return instance of clazz.
     * <p>
     * If clazz doesn't include this application, IllegalArgumentException will be thrown.
     *
     * @param clazz The class type of the instance to be returned.
     * @return Instance of clazz.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<? extends Service> clazz) {
        boolean containsKey = serviceMap.containsKey(clazz);
        if (containsKey) {
            return (T) serviceMap.get(clazz);
        }
        throw new IllegalArgumentException("Not contains key: " + clazz.getSimpleName());
    }

    public void reloadMetadata() {
        Metadata read = metadataHandler.read();
        metadata.initialize(read.getLocation(), read.getProjectList());
    }
}