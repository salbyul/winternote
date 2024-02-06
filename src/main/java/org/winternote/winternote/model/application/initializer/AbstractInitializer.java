package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.metadata.MetadataHandler;

public abstract class AbstractInitializer implements Initializer {

    private final MetadataHandler metadataHandler;

    protected abstract void fixLocation();

    protected abstract void fixRecentProjects();

    protected AbstractInitializer(final MetadataHandler metadataHandler) {
        if (isFirstTimeRunning()) {
            initialize();
        }
        this.metadataHandler = metadataHandler;
    }

    public MetadataHandler getMetadataReader() {
        return metadataHandler;
    }
}
