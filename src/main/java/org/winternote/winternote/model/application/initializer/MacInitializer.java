package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.UnknownException;

import java.io.*;

import static org.winternote.winternote.model.application.initializer.ApplicationInitializer.*;

public class MacInitializer implements Initializer {

    private final String applicationPath;
    private final String userName;
    private int numberOfRetrying = 0;


    public MacInitializer(final String applicationPath, final String userName) {
        this.applicationPath = applicationPath;
        this.userName = userName;
    }

    @Override
    public void initialize() {
        if (!isFirstTimeRunning()) {
            throw new UnsupportedOperationException("Application has already been initialized.");
        }
        structure();
        initializeMetaDataFile();
    }

    private void initializeMetaDataFile() {
        File file = new File(applicationPath + "/metadata.wn");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            writer.write("Location: /Users/" + userName + "/Desktop\n");
            writer.write("recent projects: [\n]");
            writer.flush();
        } catch (IOException e) {
            if (numberOfRetrying >= MAX_NUMBER_OF_RETRYING) {
                throw new UnknownException("Could not initialize", e);
            }
            numberOfRetrying++;
            initialize();
        }
    }

    private void structure() {
        new File(applicationPath).mkdirs();
    }

    @Override
    public boolean isFirstTimeRunning() {
        File file = new File(applicationPath + "/metadata.wn");
        return !file.exists();
    }
}
