package org.winternote.winternote.project.persistence;

import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.project.exception.ProjectCreationException;

import java.io.File;

@Persistence
public class ProjectPersistence {

    public void createProject(final Project project) {
        File file = new File(project.getPath());
        if (!file.exists() && (!file.mkdirs()))
            throw new ProjectCreationException();
    }
}
