package org.winternote.winternote.model.application.initializer;

import org.winternote.winternote.model.metadata.Metadata;

import java.io.Closeable;

public interface Initializer extends Closeable {

    void initialize();

    boolean isFirstTimeRunning();

    Metadata getMetadata();
}
