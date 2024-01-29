package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.metadata.Metadata;

import java.io.Closeable;

public interface Initializer extends Closeable {

    /**
     * When the application is first run, the application's folder structure and metadata are created.
     */
    void initialize();

    /**
     * It checks the application runs for the first time.
     *
     * @return Whether the application is launched for the first time.
     */
    boolean isFirstTimeRunning();

    /**
     * It returns metadata.
     *
     * @return metadata
     */
    Metadata getMetadata();
}
