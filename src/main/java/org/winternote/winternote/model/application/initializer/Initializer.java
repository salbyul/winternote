package org.winternote.winternote.model.application.initializer;

public interface Initializer {

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
}
