package org.winternote.winternote.project.persistence;

import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.project.exception.ProjectCreationException;

import java.io.File;

@Persistence
public class ProjectPersistence {

    public void createProjectDirectory(final String path) {
        File file = new File(path);
        if (!file.exists() && (!file.mkdirs()))
            throw new ProjectCreationException();
    }
}
