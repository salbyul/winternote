package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.metadata.Metadata;

import java.io.*;

import static org.winternote.winternote.model.application.ApplicationManager.*;
import static org.winternote.winternote.model.application.initializer.ApplicationInitializer.*;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class MacInitializer extends AbstractInitializer {

    private static final String DELIMITER = "/";
    private int numberOfRetrying = 0;

    @Override
    public void initialize() {
        structure();
        generateMetaDataFile();
    }

    private void generateMetaDataFile() {
        File file = new File(APPLICATION_PATH + DELIMITER + METADATA_NAME);
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
        writer.append("Location: /Users/").append(USER_NAME).append("/Desktop\n");
        writer.flush();
    }

    private void writeRecentProjects(final PrintWriter writer) {
        writer.append("recent projects: [\n]");
        writer.flush();
    }

    private void structure() {
        new File(APPLICATION_PATH).mkdirs();
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
