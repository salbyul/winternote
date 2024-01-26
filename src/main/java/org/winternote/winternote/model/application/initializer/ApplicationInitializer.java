package org.winternote.winternote.model.application.initializer;

import static org.winternote.winternote.model.property.PrivateProperty.isMac;

public final class ApplicationInitializer implements Initializer {

    private Initializer initializer;

    public ApplicationInitializer() {
        if (isMac()) {
            initializer = new MacInitializer();
        }
    }

    @Override
    public void initialize() {
        if (!isFirstTimeRunning()) {
            throw new UnsupportedOperationException("Application has already been initialized.");
        }
        initializer.initialize();
    }

    @Override
    public boolean isFirstTimeRunning() {
        return initializer.isFirstTimeRunning();
    }
}