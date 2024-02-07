package org.winternote.winternote.model.metadata;

import org.winternote.winternote.metadata.exception.UndeleteableMetadataException;
import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.exception.PollutedMetadataException;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.winternote.winternote.model.metadata.MetadataElement.LOCATION;
import static org.winternote.winternote.model.metadata.MetadataElement.RECENT_PROJECTS;
import static org.winternote.winternote.model.property.PublicProperty.*;

public class Metadata {
    private final WinterLogger logger = WinterLogger.instance();

    private String location;
    private final List<Project> projectList;
    private final MetadataHandler metadataHandler;

    public Metadata(final String applicationPath) {
        this.metadataHandler = new MetadataHandler(applicationPath + DELIMITER + METADATA_NAME, logger);
        this.location = metadataHandler.readLocation();
        this.projectList = metadataHandler.readRecentProjectList();
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
    public List<Project> getProjectList() {
        return Collections.unmodifiableList(this.projectList);
    }

    /**
     * Write attributes of project in recent projects list of metadata.
     *
     * @param project Project to be saved.
     */
    public void addRecentProject(final Project project) {
        synchronized (this) {
            metadataHandler.addRecentProject(project);
            reload();
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
        this.projectList.clear();
        this.projectList.addAll(metadataHandler.readRecentProjectList());
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

        private List<Project> readRecentProjectList() {
            boolean containsRecentProject = false;
            int projectIndex = 0;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(RECENT_PROJECTS.getValue())) {
                    containsRecentProject = true;
                    projectIndex = i + 1;
                    break;
                }
            }
            if (!containsRecentProject) {
                throw new PollutedMetadataException("Metadata doesn't include \"recent projects\"", RECENT_PROJECTS);
            }
            List<Project> projectList = new ArrayList<>();
            for (String line = lines.get(projectIndex); !line.equals("]"); line = lines.get(++projectIndex)) {
                String[] split = line.trim().split(":");
                String name = split[0];
                String path = split[1].substring(0, split[1].length() - 1).trim();
                projectList.add(new Project(name, path));
            }
            return projectList;
        }

        /**
         * Write attributes of project in recent projects list of metadata.
         *
         * @param project Project to be saved.
         */
        public void addRecentProject(final Project project) {
            synchronized (this) {
                String value = "\t" + project.toString() + "/" + project.getName() + ",";
                readAllLines();

                boolean containsRecentProject = false;
                int projectIndex = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith(RECENT_PROJECTS.getValue())) {
                        containsRecentProject = true;
                        projectIndex = i + 1;
                        break;
                    }
                }
                if (!containsRecentProject) {
                    throw new PollutedMetadataException("Metadata doesn't include \"recent projects\"", RECENT_PROJECTS);
                }

                lines.add(projectIndex, value);
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
                throw new UndeleteableMetadataException("Failed to reload ConfigFile.");
            }
        }
    }
}