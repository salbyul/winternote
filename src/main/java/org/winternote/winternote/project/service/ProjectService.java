package org.winternote.winternote.project.service;

import org.springframework.stereotype.Service;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.project.persistence.ProjectPersistence;

@Service
public class ProjectService {

    private final ProjectPersistence projectPersistence;
    private final WinterLogger logger;

    public ProjectService(final ProjectPersistence projectPersistence, final WinterLogger logger) {
        this.projectPersistence = projectPersistence;
        this.logger = logger;
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
        logger.logNewProject(projectName, path);
        projectPersistence.makeDirectory(path);
        return project;
    }

}
