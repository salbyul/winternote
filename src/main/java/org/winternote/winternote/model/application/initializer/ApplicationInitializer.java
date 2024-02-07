package org.winternote.winternote.model.application.initializer;

import static org.winternote.winternote.model.property.PrivateProperty.*;

public final class ApplicationInitializer implements Initializer {

    private AbstractInitializer specificInitializer;
    public static final int MAX_NUMBER_OF_RETRYING = 5;

    public ApplicationInitializer() {
        if (isMac()) {
            specificInitializer = new MacInitializer();
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
}