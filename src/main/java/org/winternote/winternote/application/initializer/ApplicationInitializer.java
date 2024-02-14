package org.winternote.winternote.application.initializer;

import org.springframework.stereotype.Component;
import org.winternote.winternote.application.property.PrivateProperty;

@Component
public final class ApplicationInitializer implements Initializer {

    private Initializer specificInitializer;
    public static final int MAX_NUMBER_OF_RETRYING = 5;

    public ApplicationInitializer(final PrivateProperty privateProperty) {
        if (privateProperty.getOS().isMac()) {
            specificInitializer = new MacInitializer(privateProperty);
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