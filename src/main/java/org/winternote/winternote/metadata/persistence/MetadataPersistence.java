package org.winternote.winternote.metadata.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.metadata.exception.UndeletableMetadataException;
import org.winternote.winternote.application.initializer.exception.InitialException;
import org.winternote.winternote.metadata.exception.PollutedMetadataException;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static org.winternote.winternote.metadata.persistence.MetadataElement.LOCATION;
import static org.winternote.winternote.metadata.persistence.MetadataElement.RECENT_NOTES;
import static org.winternote.winternote.application.property.PublicProperty.*;

@Persistence
@DependsOn({"applicationManager"})
public class MetadataPersistence {

    private String location;
    private final List<NoteSummary> recentNoteList;
    private final MetadataHandler metadataHandler;

    @Autowired
    public MetadataPersistence(final PrivateProperty property, final WinterLogger logger) {
        this.metadataHandler = new MetadataHandler(property.getApplicationPath() + DELIMITER + METADATA_NAME, logger);
        this.location = metadataHandler.readLocation();
        this.recentNoteList = metadataHandler.readRecentNoteSummaryList();
    }

    /**
     * Return location.
     *
     * @return location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Return unmodifiable project list.
     *
     * @return Project list.
     */
    public List<NoteSummary> getRecentNoteList() {
        return Collections.unmodifiableList(this.recentNoteList);
    }

    /**
     * Write attributes of note in recent notes list of metadata.
     *
     * @param note Note to be saved.
     * @return When added, true.
     */
    public boolean addRecentNote(final Note note) {
        synchronized (this) {
            boolean added = metadataHandler.addRecentNote(note);
            if (added) {
                reload();
                return true;
            }
            return false;
        }
    }

    /**
     * Change location property in metadata.
     *
     * @param newLocation New location.
     */
    public void changeLocation(final String newLocation) {
        synchronized (this) {
            metadataHandler.changeLocation(newLocation);
            reload();
        }
    }

    /**
     * Reload fields from metadata file.
     * Must be invoked after modifying metadata.
     */
    private void reload() {
        this.location = metadataHandler.readLocation();
        this.recentNoteList.clear();
        this.recentNoteList.addAll(metadataHandler.readRecentNoteSummaryList());
    }

    private static class MetadataHandler {

        private final WinterLogger logger;
        private final String metadataPath;
        private final List<String> lines;

        private MetadataHandler(final String metadataPath, final WinterLogger logger) {
            this.metadataPath = metadataPath;
            this.logger = logger;
            lines = new ArrayList<>();
            readAllLines();
        }

        private void readAllLines() {
            lines.clear();
            BufferedReader reader = generateReader();
            reader.lines().forEach(lines::add);
            close(reader);
        }

        private BufferedReader generateReader() {
            try {
                return new BufferedReader(new FileReader(metadataPath));
            } catch (IOException e) {
                throw new InitialException(e);
            }
        }

        private String readLocation() {
            String locationLine = lines.stream()
                    .filter(line -> line.startsWith(LOCATION.getValue() + ": "))
                    .findFirst()
                    .orElseThrow(() -> new PollutedMetadataException("Metadata doesn't include \"Location\"", LOCATION));
            return locationLine.split(" ")[1];
        }

        private List<NoteSummary> readRecentNoteSummaryList() {
            boolean containsRecentNote = false;
            int noteIndex = 0;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(RECENT_NOTES.getValue())) {
                    containsRecentNote = true;
                    noteIndex = i + 1;
                    break;
                }
            }
            if (!containsRecentNote) {
                throw new PollutedMetadataException("Metadata doesn't include \"recent notes\"", RECENT_NOTES);
            }
            List<NoteSummary> recentNoteList = new ArrayList<>();
            for (String line = lines.get(noteIndex); !line.equals("]"); line = lines.get(++noteIndex)) {
                String[] split = line.trim().split(":");
                String name = split[0];
                String location = split[1].substring(0, split[1].length() - 1).trim();
                recentNoteList.add(new NoteSummary(name, location));
            }
            return recentNoteList;
        }

        /**
         * Write attributes of note in recent notes list of metadata.
         *
         * @param note Note to be saved.
         */
        public boolean addRecentNote(final Note note) {
            synchronized (this) {
                String willBeAdded = "\t" + note.getName() + ": " + note.getLocation() + ",";
                readAllLines();

                boolean containsRecentNote = false;
                int noteIndex = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith(RECENT_NOTES.getValue())) {
                        containsRecentNote = true;
                        noteIndex = i + 1;
                        break;
                    }
                }
                if (!containsRecentNote) {
                    throw new PollutedMetadataException("Metadata doesn't include \"recent notes\"", RECENT_NOTES);
                }

                lines.add(noteIndex, willBeAdded);
                for (int i = noteIndex + 1; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith(willBeAdded)) {
                        lines.remove(i);
                        break;
                    }
                }
                deleteMetadata();
                BufferedWriter writer = generateWriter();
                try {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                    writer.flush();
                } catch (IOException e) {
                    throw new InitialException(e);
                }
                close(writer);
                return true;
            }
        }

        private BufferedWriter generateWriter() {
            try {
                return new BufferedWriter(new FileWriter(metadataPath));
            } catch (IOException e) {
                throw new InitialException(e);
            }
        }

        /**
         * Change location property in metadata.
         *
         * @param newLocation New location.
         */
        public void changeLocation(final String newLocation) {
            synchronized (this) {
                readAllLines();

                boolean containsLocation = false;
                int locationIndex = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith(LOCATION.getValue())) {
                        containsLocation = true;
                        locationIndex = i;
                        break;
                    }
                }
                if (!containsLocation) {
                    throw new PollutedMetadataException("Metadata doesn't include \"Location\"", LOCATION);
                }
                String location = lines.get(locationIndex);
                lines.set(locationIndex, location.split(": ")[0] + ": " + newLocation);
                deleteMetadata();
                BufferedWriter writer = generateWriter();
                try {
                    for (String line : lines) {
                        writer.write(line + "\n");
                    }
                    writer.flush();
                } catch (IOException e) {
                    throw new InitialException(e);
                }
                close(writer);
            }
        }

        private void close(final Writer writer) {
            if (Objects.nonNull(writer)) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.logException(e);
                }
            }
        }

        private void close(final Reader reader) {
            if (Objects.nonNull(reader)) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.logException(e);
                }
            }
        }

        private void deleteMetadata() {
            File file = new File(metadataPath);
            try {
                Files.delete(file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                logger.logException(e);
                throw new UndeletableMetadataException("Failed to reload ConfigFile.");
            }
        }
    }
}