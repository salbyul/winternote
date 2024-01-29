package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.MetadataElement;
import org.winternote.winternote.model.exception.PollutedMetadataException;
import org.winternote.winternote.model.metadata.Metadata;

import java.io.IOException;
import java.util.Objects;

import static org.winternote.winternote.model.application.ApplicationManager.*;

public final class ApplicationInitializer implements Initializer {

    private AbstractInitializer specificInitializer;
    public static final int MAX_NUMBER_OF_RETRYING = 5;

    public ApplicationInitializer() {
        if (isMac()) {
            specificInitializer = new MacInitializer();
        }
    }

    @Override
    public void initialize() {
        if (isFirstTimeRunning()) {
            specificInitializer.initialize();
        }
    }

    @Override
    public boolean isFirstTimeRunning() {
        return specificInitializer.isFirstTimeRunning();
    }

    @Override
    public Metadata getMetadata() {
        if (isFirstTimeRunning()) {
            initialize();
        }
        try {
            return specificInitializer.getMetadata();
        } catch (PollutedMetadataException e) {
            MetadataElement element = e.getElement();
            if (Objects.requireNonNull(element) == MetadataElement.LOCATION) {
                fixLocation();
            } else if (element == MetadataElement.RECENT_PROJECTS) {
                fixRecentProjects();
            }
            return getMetadata();
        }
    }

    private void fixLocation() {
        specificInitializer.fixLocation();
    }

    private void fixRecentProjects() {
        specificInitializer.fixRecentProjects();
    }

    @Override
    public void close() throws IOException {
        specificInitializer.close();
    }
}