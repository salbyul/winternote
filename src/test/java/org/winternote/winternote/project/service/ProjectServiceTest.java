package org.winternote.winternote.project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.project.repository.ProjectRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    WinterLogger winterLogger;

    @InjectMocks
    ProjectService projectService;

    @Test
    void createProject() {
        // given
        String projectName = "name";
        String path = "path";

        // when
        doNothing().when(winterLogger).logNewProject(projectName, path);
        doNothing().when(projectRepository).createProject(any(Project.class));
        Project project = projectService.createProject(projectName, path);

        // then
        assertThat(project.getName()).isEqualTo(projectName);
        assertThat(project.getPath()).isEqualTo(path);
    }
}