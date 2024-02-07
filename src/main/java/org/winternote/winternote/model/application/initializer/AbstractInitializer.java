package org.winternote.winternote.model.application.initializer;

public abstract class AbstractInitializer implements Initializer {

    protected AbstractInitializer() {
        if (isFirstTimeRunning()) {
            initialize();
        }
    }
}
