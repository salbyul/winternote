package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.metadata.MetadataElement;
import org.winternote.winternote.model.exception.PollutedMetadataException;
import org.winternote.winternote.model.metadata.Metadata;
import org.winternote.winternote.model.metadata.MetadataHandler;

import java.util.Objects;

import static org.winternote.winternote.model.application.ApplicationManager.*;

public final class ApplicationInitializer implements Initializer {

    private final MetadataHandler metadataHandler;
    private AbstractInitializer specificInitializer;
    public static final int MAX_NUMBER_OF_RETRYING = 5;

    public ApplicationInitializer(final MetadataHandler metadataHandler) {
        this.metadataHandler = metadataHandler;
        if (isMac()) {
            specificInitializer = new MacInitializer(metadataHandler);
        }
    }

    @Override
    public void initialize() {
        specificInitializer.initialize();
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
}