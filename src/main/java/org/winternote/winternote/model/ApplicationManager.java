package org.winternote.winternote.model;

import org.winternote.winternote.model.exception.UnSupportedOSException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.winternote.winternote.property.PrivateProperty.*;
import static org.winternote.winternote.property.PrivateProperty.isMac;

public class ApplicationManager {

    private final String APPLICATION_PATH;

    public ApplicationManager() {
        APPLICATION_PATH = generateApplicationPath();
    }

    private String generateApplicationPath() {
        if (isMac()) {
            return generateApplicationPathForMac();
        }
        throw new UnSupportedOSException("Not supported OS: " + OS);
    }

    private String generateApplicationPathForMac() {
        Path path = Paths.get("/Users/" + USER_NAME + "/winternote/projects");
        if (!Files.exists(path)) {
            new ApplicationInitializer().initialize();
        }
        return "/Users/" + USER_NAME + "/winternote/projects/";
    }
}
