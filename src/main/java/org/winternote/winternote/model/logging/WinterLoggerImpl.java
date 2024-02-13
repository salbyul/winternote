package org.winternote.winternote.model.logging;

import org.springframework.stereotype.Component;
import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.exception.LoggingException;
import org.winternote.winternote.model.property.PrivateProperty;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

import static java.util.logging.Level.*;
import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

@Component
public class WinterLoggerImpl implements WinterLogger {

    private final PrivateProperty property;
    private final Logger logger = Logger.getLogger("");

    public WinterLoggerImpl(final PrivateProperty property) {
        this.property = property;
        removeAllLoggerHandler();

        try {
            initializeLogFile();
            setLoggerHandler();
        } catch (IOException e) {
            throw new LoggingException("Logging file initialization failed.", e);
        }
    }

    private void initializeLogFile() {
        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        File file = new File(property.getApplicationPath() + "/logging/" + today + DELIMITER);
        if (!file.exists() && (!file.mkdirs()))
            throw new InitialException("Failed to initialize log file.");
    }

    private void removeAllLoggerHandler() {
        Handler[] handlers = logger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            logger.removeHandler(handlers[i]);
        }
    }

    private void setLoggerHandler() throws IOException {
        String today = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        Handler handler = new FileHandler(property.getApplicationPath() + "/logging/" + today + DELIMITER + today + "[%g].log", true);
        WinterFormatter formatter = new WinterFormatter();
        handler.setFormatter(formatter);
        logger.setLevel(INFO);
        logger.addHandler(handler);
    }

    @Override
    public void logNewProject(final String projectName, final String path) {
        synchronized (this) {
            logger.log(INFO, () -> "New project called '%s' created at %s".formatted(projectName, path));
        }
    }

    @Override
    public void logNewNote(final String noteName, final String projectName) {
        synchronized (this) {
            logger.log(INFO, () -> "New note called '%s' created in %s".formatted(noteName, projectName));
        }
    }

    @Override
    public void logAutoSave(final String noteName, final String projectName) {
        synchronized (this) {
            logger.log(INFO, () -> "Auto saved '%s' in '%s'".formatted(noteName, projectName));
        }
    }

    @Override
    public void logSave(final String noteName, final String projectName) {
        synchronized (this) {
            logger.log(INFO, () -> "Saved '%s' in '%s'".formatted(noteName, projectName));
        }
    }

    @Override
    public void logDeletedProject(final String projectName, final String path) {
        synchronized (this) {
            logger.log(INFO, () -> "'%s' at '%s' deleted".formatted(projectName, path));
        }
    }

    @Override
    public void logDeletedNote(final String noteName, final String projectName) {
        synchronized (this) {
            logger.log(INFO, () -> "'%s' in '%s' deleted".formatted(noteName, projectName));
        }
    }

    @Override
    public void logException(final Throwable throwable) {
        synchronized (this) {
            try (StringWriter sw = new StringWriter();
                 PrintWriter writer = new PrintWriter(sw)) {
                throwable.printStackTrace(writer);
                logger.log(SEVERE, sw::toString);
            } catch (Exception e) { // unreachable
            }
        }
    }

    @Override
    public void logAddedRecentProjects(final String value) {
        synchronized (this) {
            logger.log(INFO, () -> "%s is added in recent projects.".formatted(value));
        }
    }

    @Override
    public void logChangedLocation(final String oldLocation, final String newLocation) {
        synchronized (this) {
            logger.log(INFO, () -> "Changed location '%s' to '%s'".formatted(oldLocation, newLocation));
        }
    }

    private static class WinterFormatter extends Formatter {

        @Override
        public String format(final LogRecord logRecord) {
            StringBuilder builder = new StringBuilder();
            String format = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss] ")
                    .format(new Date(logRecord.getMillis()));
            builder.append(format)
                    .append("[").append(logRecord.getLevel()).append("]").append(": ")
                    .append(logRecord.getMessage())
                    .append("\n");

            return builder.toString();
        }
    }
}
