package org.winternote.winternote.project.service;

import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.exception.ProjectEmptyTitleException;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.common.service.Service;
import org.winternote.winternote.project.repository.ProjectRepository;

public class ProjectService implements Service {

    private final ProjectRepository projectRepository;
    private final WinterLogger logger = WinterLogger.instance();

    public ProjectService(final ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
        validateProject(project);
        logger.logNewProject(projectName, path);
        projectRepository.createProject(project);
        return project;
    }

    private void validateProject(final Project project) {
        String name = project.getName();
        if (name.isEmpty()) {
            throw new ProjectEmptyTitleException();
        }
    }
}
