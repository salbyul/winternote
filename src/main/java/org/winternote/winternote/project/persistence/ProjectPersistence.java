package org.winternote.winternote.project.persistence;

import org.winternote.winternote.common.annotation.Persistence;
import org.winternote.winternote.project.exception.ProjectCreationException;

import java.io.File;

@Persistence
public class ProjectPersistence {

    /**
     * Make a directory to path.
     *
     * @param path Path where the directory will be made.
     */
    public void makeDirectory(final String path) {
        File file = new File(path);
        if (!file.exists() && (!file.mkdirs()))
            throw new ProjectCreationException();
    }
}
