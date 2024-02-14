package org.winternote.winternote.application;

import org.springframework.stereotype.Component;
import org.winternote.winternote.application.initializer.Initializer;

@Component
public class ApplicationManager {

    private final Initializer initializer;

    public ApplicationManager(final Initializer initializer) {
        this.initializer = initializer;
        initialize(); // TODO Must be invoked outside of this class.
    }

    /**
     * If this application run for the first time, it will initialize.
     */
    public void initialize() {
        if (initializer.isFirstTimeRunning()) {
            initializer.initialize();
        }
    }
}