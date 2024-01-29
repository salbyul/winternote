package org.winternote.winternote.model.metadata;

import org.winternote.winternote.model.exception.PollutedMetadataException;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.winternote.winternote.model.metadata.MetadataElement.LOCATION;
import static org.winternote.winternote.model.metadata.MetadataElement.RECENT_PROJECTS;

public class MetadataReader implements Closeable {

    private final BufferedReader reader;
    private final List<String> lines = new ArrayList<>();

    public MetadataReader(final Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    private void readAllLines() {
        reader.lines().forEach(lines::add);
    }

    /**
     * After reading a metadata file, it creates and returns a Metadata instance.
     *
     * @return Metadata
     */
    public Metadata read() {
        readAllLines();
        return new Metadata(readLocation(), readRecentProjectList());
    }

    private String readLocation() {
        String locationLine = lines.stream()
                .filter(line -> line.startsWith(LOCATION.getValue()))
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

    @Override
    public void close() throws IOException {
        reader.close();
    }
}