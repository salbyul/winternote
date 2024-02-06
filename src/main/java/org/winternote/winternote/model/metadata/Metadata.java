package org.winternote.winternote.model.metadata;

import org.winternote.winternote.project.domain.Project;

import java.util.Collections;
import java.util.List;

public class Metadata {

    private String location;
    private final List<Project> projectList;

    public Metadata(final String location, final List<Project> projectList) {
        this.location = location;
        this.projectList = projectList;
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
     * Initialize location and projects list.
     * Need to invoke this method when metadata is modified.
     *
     * @param location    New location.
     * @param projectList New project list.
     */
    public void initialize(final String location, final List<Project> projectList) {
        this.location = location;
        this.projectList.clear();
        this.projectList.addAll(projectList);
    }
}