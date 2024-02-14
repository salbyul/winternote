package org.winternote.winternote.application.initializer;

import org.winternote.winternote.application.initializer.exception.InitialException;
import org.winternote.winternote.application.property.PrivateProperty;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.winternote.winternote.application.initializer.ApplicationInitializer.*;
import static org.winternote.winternote.metadata.persistence.MetadataElement.*;
import static org.winternote.winternote.application.property.PublicProperty.*;

public class MacInitializer implements Initializer {

    private int numberOfRetrying = 0;
    private final PrivateProperty property;

    protected MacInitializer(final PrivateProperty property) {
        this.property = property;
    }

    @Override
    public void initialize() {
        structure();
        generateMetaDataFile();
    }

    private void generateMetaDataFile() {
        File file = new File(property.getApplicationPath() + DELIMITER + METADATA_NAME);
        if (file.exists()) {
            return;
        }
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writeLocation(writer);
            writeRecentNotes(writer);
        } catch (IOException e) {
            if (numberOfRetrying >= MAX_NUMBER_OF_RETRYING) {
                throw new InitialException(e);
            }
            numberOfRetrying++;
            initialize();
        }
    }

    private void writeLocation(final PrintWriter writer) {
        writer.append(LOCATION.getValue()).append(": /Users/").append(property.getUsername()).append("/Desktop\n");
        writer.flush();
    }

    private void writeRecentNotes(final PrintWriter writer) {
        writer.append(RECENT_NOTES.getValue()).append(": [\n]");
        writer.flush();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void structure() {
        new File(property.getApplicationPath()).mkdirs();

        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        new File(property.getApplicationPath() + "/logging/" + today).mkdirs();
    }

    @Override
    public boolean isFirstTimeRunning() {
        File file = new File(property.getApplicationPath() + DELIMITER + METADATA_NAME);
        return !file.exists();
    }
}
