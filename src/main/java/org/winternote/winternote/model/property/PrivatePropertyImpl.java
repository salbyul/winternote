package org.winternote.winternote.model.property;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.springframework.stereotype.Component;
import org.winternote.winternote.model.exception.UnsupportedOSException;

@Component
public class PrivatePropertyImpl implements PrivateProperty {

    public final double displayWidth;
    public final double displayHeight;
    public final OS os;
    public final String username;
    public final String applicationPath;

    public PrivatePropertyImpl() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        displayWidth = bounds.getWidth();
        displayHeight = bounds.getHeight();
        os = OS.findOperatingSystem();
        username = System.getProperty("user.name");
        applicationPath = generateApplicationPath();
    }

    private String generateApplicationPath() {
        if (os.isMac()) {
            return "/Users/" + username + "/winternote";
        }
        throw new UnsupportedOSException("Not supported OS: " + os);
    }

    @Override
    public double getDisplayWidth() {
        return displayWidth;
    }

    @Override
    public double getDisplayHeight() {
        return displayHeight;
    }

    @Override
    public OS getOS() {
        return os;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getApplicationPath() {
        return applicationPath;
    }
}
