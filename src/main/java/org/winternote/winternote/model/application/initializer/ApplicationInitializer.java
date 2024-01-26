package org.winternote.winternote.model.application.initializer;

import static org.winternote.winternote.model.property.PrivateProperty.*;

public final class ApplicationInitializer implements Initializer {

    private Initializer initializer;
    public static final int MAX_NUMBER_OF_RETRYING = 5;

    public ApplicationInitializer(final String applicationPath, final String userName) {
        if (isMac()) {
            initializer = new MacInitializer(applicationPath, userName);
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