package org.winternote.winternote.project.service;

import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.exception.ProjectCreationException;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.common.service.Service;
import org.winternote.winternote.project.repository.ProjectRepository;

import java.util.Objects;

public class ProjectService implements Service {

    private final ProjectRepository projectRepository;
    private final WinterLogger logger;

    public ProjectService(final ProjectRepository projectRepository, final WinterLogger logger) {
        this.projectRepository = projectRepository;
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
        validateProject(project);
        logger.logNewProject(projectName, path);
        projectRepository.createProject(project);
        return project;
    }

    private void validateProject(final Project project) {
        String name = project.getName();
        if (Objects.isNull(name) || name.isEmpty() || name.isBlank()) {
            throw new ProjectCreationException("The project name is empty.");
        }
        String path = project.getPath();
        if (Objects.isNull(path) || path.isEmpty() || path.isBlank()) {
            throw new ProjectCreationException("The project path is empty.");
        }
    }
}
