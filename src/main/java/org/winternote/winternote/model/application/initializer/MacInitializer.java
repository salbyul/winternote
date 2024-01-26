package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.UnknownException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.winternote.winternote.model.property.PrivateProperty.*;
import static org.winternote.winternote.model.property.PrivateProperty.USER_NAME;

public class MacInitializer implements Initializer {

    @Override
    public void initialize() {
        if (!isFirstTimeRunning()) {
            throw new UnsupportedOperationException("Application has already been initialized.");
        }
        initializeDefaultDirectoryStructure();
        initializeMetaDataFile();
    }

    private void initializeMetaDataFile() {
        File file = new File(APPLICATION_PATH + "/metadata.wn");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            writer.print("Location: " + APPLICATION_PATH + "/projects\n");
            writer.print("recent projects: [\n]");
            writer.flush();
        } catch (IOException e) {
            throw new UnknownException("Unknown Error occurred.", e);
        }
    }

    private static void initializeDefaultDirectoryStructure() {
        boolean isCreated = new File("/Users/" + USER_NAME + "/winternote/projects").mkdirs();
        if (!isCreated) {
            throw new UnknownException("Could not initialize");
        }
    }

    @Override
    public boolean isFirstTimeRunning() {
        Path path = Paths.get(APPLICATION_PATH + "/projects");
        File metadata = new File(APPLICATION_PATH + "/metadata.wn");
        return !Files.exists(path) || !metadata.exists();
    }
}
