package org.winternote.winternote.model.application;

import org.winternote.winternote.model.application.initializer.Initializer;
import org.winternote.winternote.model.exception.UnsupportedOSException;
import org.winternote.winternote.model.application.initializer.ApplicationInitializer;

import static org.winternote.winternote.model.property.PrivateProperty.*;

public class ApplicationManager {

    private static ApplicationManager instance;
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static ApplicationManager instance(final String applicationPath, final String userName) {
        if (instance == null) {
            instance = new ApplicationManager(new ApplicationInitializer(applicationPath, userName));
        }
        return instance;
    }

    private ApplicationManager(final Initializer initializer) {
        if (!isSupportedOS()) {
            throw new UnsupportedOSException("Not supported OS: " + OS);
        }
        if (instance != null) { // prevent reflect
            throw new UnsupportedOperationException("ApplicationManager has already been initialized. If you want to get instance, you need to invoke instance method.");
        }

        if (initializer.isFirstTimeRunning()) {
            initializer.initialize();
        }
    }
}