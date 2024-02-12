package org.winternote.winternote.model.property;

import org.winternote.winternote.model.exception.UnsupportedOSException;

import java.util.Arrays;

public enum OS {
    MAC("Mac");

    private final String name;

    OS(final String name) {
        this.name = name;
    }

    public static OS findOperatingSystem() {
        String os = System.getProperty("os.name");
        return Arrays.stream(values()).filter(e -> os.startsWith(e.toString()))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOSException(String.format("Unsupported OS: %s", os)));
    }

    /**
     * It checks user's platform is Mac OS.
     *
     * @return Whether user's platform is Mac OS.
     */
    public boolean isMac() {
        return this == MAC;
    }

    @Override
    public String toString() {
        return name;
    }
}
