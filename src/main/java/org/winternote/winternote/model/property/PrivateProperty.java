package org.winternote.winternote.model.property;

public interface PrivateProperty {

    /**
     * Gets the display width.
     *
     * @return Width.
     */
    double getDisplayWidth();

    /**
     * Gets the display height.
     *
     * @return Height.
     */
    double getDisplayHeight();

    /**
     * Gets the operating system.
     *
     * @return Operating system.
     */
    OS getOS();

    /**
     * Gets the username.
     *
     * @return Username
     */
    String getUsername();

    /**
     * Gets the application path.
     *
     * @return Application path.
     */
    String getApplicationPath();
}
