package org.winternote.winternote.project.domain;

import org.winternote.winternote.project.exception.ProjectCreationException;

import java.util.Objects;

public class Project {

    private final String name;
    private final String path;

    public Project(final String name, final String path) {
        this.name = name;
        this.path = path;
        if (Objects.isNull(name) || name.isEmpty() || name.isBlank()) {
            throw new ProjectCreationException("The project name is empty.");
        }
        if (Objects.isNull(path) || path.isEmpty() || path.isBlank()) {
            throw new ProjectCreationException("The project path is empty.");
        }
    }

    @Override
    public String toString() {
        return name + ": " + path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}