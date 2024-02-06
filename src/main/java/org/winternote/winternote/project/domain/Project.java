package org.winternote.winternote.project.domain;

import java.util.Objects;

public class Project {

    private final String name;
    private final String path;

    public Project(final String name, final String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) && Objects.equals(path, project.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
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