package org.winternote.winternote.model.metadata;

import org.winternote.winternote.model.application.ApplicationManager;
import org.winternote.winternote.model.exception.InitialException;
import org.winternote.winternote.model.exception.PollutedMetadataException;
import org.winternote.winternote.project.domain.Project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.winternote.winternote.model.application.initializer.MacInitializer.DELIMITER;
import static org.winternote.winternote.model.metadata.MetadataElement.LOCATION;
import static org.winternote.winternote.model.metadata.MetadataElement.RECENT_PROJECTS;
import static org.winternote.winternote.model.property.PublicProperty.METADATA_NAME;

public class MetadataHandler {

    private static final MetadataHandler instance = new MetadataHandler(ApplicationManager.APPLICATION_PATH + DELIMITER + METADATA_NAME);
    private final String metadataPath;
    private final List<String> lines = new ArrayList<>();

    private MetadataHandler(final String metadataPath) {
        if (Objects.nonNull(instance)) { // prevent reflect
            throw new UnsupportedOperationException("MetadataHandler has already been initialized. If you want to get instance, you need to invoke instance method.");
        }
        this.metadataPath = metadataPath;
    }

    /**
     * Get a MetadataHandler instance.
     *
     * @return instance;
     */
    public static MetadataHandler instance() {
        return instance;
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

    /**
     * After reading a metadata file, it creates and returns a Metadata instance.
     *
     * @return Metadata
     */
    public Metadata read() {
        synchronized (instance) {
            readAllLines();
            return new Metadata(readLocation(), readRecentProjectList());
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
     * Write project in recent projects list in metadata.
     *
     * @param project Project to be saved.
     */
    public void addRecentProject(final Project project) {
        synchronized (instance) {
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
            ApplicationManager applicationManager = ApplicationManager.instance();
            applicationManager.reloadMetadata();
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
        synchronized (instance) {
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
            ApplicationManager applicationManager = ApplicationManager.instance();
            applicationManager.reloadMetadata();
        }
    }

    private void close(final Writer writer) {
        if (Objects.nonNull(writer)) {
            try {
                writer.close();
            } catch (IOException e) {
            }
        }
    }

    private void close(final Reader reader) {
        if (Objects.nonNull(reader)) {
            try {
                reader.close();
            } catch (IOException e) {
            }
        }
    }

    private void deleteMetadata() {
        File file = new File(metadataPath);
        file.delete();
    }
}