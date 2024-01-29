package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.metadata.MetadataReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.winternote.winternote.model.application.ApplicationManager.APPLICATION_PATH;
import static org.winternote.winternote.model.property.PublicProperty.METADATA_NAME;

public abstract class AbstractInitializer implements Initializer {

    private final MetadataReader metadataReader;

    protected abstract void fixLocation();
    protected abstract void fixRecentProjects();

    protected AbstractInitializer() {
        if (isFirstTimeRunning()) {
            initialize();
        }
        try {
            this.metadataReader = new MetadataReader(new FileReader(APPLICATION_PATH + "/" + METADATA_NAME));
        } catch (FileNotFoundException e) {
            throw new InitialException(e); // unreachable exception
        }
    }

    public MetadataReader getMetadataReader() {
        return metadataReader;
    }

    @Override
    public void close() throws IOException {
        metadataReader.close();
    }
}
