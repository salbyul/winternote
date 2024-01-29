package org.winternote.winternote.model.metadata;

import java.util.List;

public class Metadata {

    private String location;
    private final List<Project> projectList;

    public Metadata(final String location, final List<Project> projectList) {
        this.location = location;
        this.projectList = projectList;
    }
}