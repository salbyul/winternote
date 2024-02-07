package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.InitialException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.winternote.winternote.model.application.initializer.ApplicationInitializer.*;
import static org.winternote.winternote.model.metadata.MetadataElement.*;
import static org.winternote.winternote.model.property.PrivateProperty.*;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class MacInitializer extends AbstractInitializer {

    private int numberOfRetrying = 0;

    protected MacInitializer() {
    }

    @Override
    public void initialize() {
        structure();
        generateMetaDataFile();
    }

    private void generateMetaDataFile() {
        File file = new File(APPLICATION_PATH + DELIMITER + METADATA_NAME);
        if (file.exists()) {
            return;
        }
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writeLocation(writer);
            writeRecentProjects(writer);
        } catch (IOException e) {
            if (numberOfRetrying >= MAX_NUMBER_OF_RETRYING) {
                throw new InitialException(e);
            }
            numberOfRetrying++;
            initialize();
        }
    }

    private void writeLocation(final PrintWriter writer) {
        writer.append(LOCATION.getValue()).append(": /Users/").append(USER_NAME).append("/Desktop\n");
        writer.flush();
    }

    private void writeRecentProjects(final PrintWriter writer) {
        writer.append(RECENT_PROJECTS.getValue()).append(": [\n]");
        writer.flush();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void structure() {
        new File(APPLICATION_PATH).mkdirs();

        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        new File(APPLICATION_PATH + "/logging/" + today).mkdirs();
    }

    @Override
    public boolean isFirstTimeRunning() {
        File file = new File(APPLICATION_PATH + DELIMITER + METADATA_NAME);
        return !file.exists();
    }
}
