package org.winternote.winternote.model.application;

import org.springframework.stereotype.Component;
import org.winternote.winternote.model.application.initializer.Initializer;

@Component
public class ApplicationManager {

    private final Initializer initializer;

    public ApplicationManager(final Initializer initializer) {
        this.initializer = initializer;
        initialize();
    }

    public void initialize() {
        if (initializer.isFirstTimeRunning()) {
            initializer.initialize();
        }
    }
}