package org.winternote.winternote.model;

import org.winternote.winternote.model.exception.UnknownException;
import org.winternote.winternote.model.property.PrivateProperty;

import java.io.File;

public class ApplicationInitializer {

    public void initialize() {
        if (PrivateProperty.isMac()) {
            initializeSettingForMac();
        }
    }

    private void initializeSettingForMac() {
        boolean isCreated = new File("/Users/" + PrivateProperty.USER_NAME + "/winternote/projects").mkdirs();
        if (!isCreated) {
            throw new UnknownException("Could not initialize");
        }
    }
}