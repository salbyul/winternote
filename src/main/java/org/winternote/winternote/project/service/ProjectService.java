package org.winternote.winternote.project.service;

import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.model.metadata.MetadataHandler;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.common.service.Service;

import java.io.File;

public class ProjectService implements Service {

    private static final ProjectService instance = new ProjectService(MetadataHandler.instance());

    private final MetadataHandler metadataHandler;

    private final WinterLogger logger = WinterLogger.instance();

    private ProjectService(final MetadataHandler metadataHandler) {
        this.metadataHandler = metadataHandler;
    }

    public static ProjectService instance() {
        return instance;
    }

    /**
     * Create a new Project.
     *
     * @param projectName Project name.
     * @param path        Path where project will be saved.
     * @return Created project.
     */
    public Project createProject(final String projectName, final String path) {
        Project project = new Project(projectName, path);
        metadataHandler.addRecentProject(project);
        logger.logNewProject(projectName, path);
        createProjectDirectory(project);
        metadataHandler.changeLocation(path);
        return project;
    }

    private void createProjectDirectory(final Project project) {
        File file = new File(project.getPath() + "/" + project.getName());
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
