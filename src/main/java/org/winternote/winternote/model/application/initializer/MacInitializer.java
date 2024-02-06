package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.metadata.Metadata;
import org.winternote.winternote.model.metadata.MetadataHandler;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.winternote.winternote.model.application.ApplicationManager.*;
import static org.winternote.winternote.model.application.initializer.ApplicationInitializer.*;
import static org.winternote.winternote.model.metadata.MetadataElement.*;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class MacInitializer extends AbstractInitializer {

    public static final String DELIMITER = "/";
    private int numberOfRetrying = 0;

    protected MacInitializer(final MetadataHandler metadataHandler) {
        super(metadataHandler);
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

    @Override
    public Metadata getMetadata() {
        return getMetadataReader().read();
    }

    @Override
    protected void fixLocation() {
        File file = new File(APPLICATION_PATH + DELIMITER + METADATA_NAME);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writeLocation(writer);
        } catch (IOException e) {
            throw new InitialException(e);
        }
    }

    @Override
    protected void fixRecentProjects() {
        File file = new File(APPLICATION_PATH + DELIMITER + METADATA_NAME);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writeRecentProjects(writer);
        } catch (IOException e) {
            throw new InitialException(e);
        }
    }
}
