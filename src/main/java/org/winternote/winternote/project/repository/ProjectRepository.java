package org.winternote.winternote.project.repository;

import org.winternote.winternote.common.repository.Repository;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.project.exception.ProjectCreationException;

import java.io.File;

import static org.winternote.winternote.model.property.PublicProperty.DELIMITER;

public class ProjectRepository implements Repository {

    public void createProject(final Project project) {
        File file = new File(project.getPath() + DELIMITER + project.getName());
        if (!file.exists() && (!file.mkdirs()))
            throw new ProjectCreationException();
    }
}
