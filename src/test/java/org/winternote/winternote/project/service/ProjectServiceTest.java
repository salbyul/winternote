package org.winternote.winternote.project.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.project.domain.Project;
import org.winternote.winternote.project.exception.ProjectCreationException;
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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If the name is empty or null, a ProjectEmptyTitleException should be thrown.")
    void failCreateProjectSinceEmptyTitle(String input) {
        // given
        String projectName = input;
        String path = "path";

        // when

        // then
        assertThatThrownBy(() -> projectService.createProject(projectName, path))
                .isInstanceOf(ProjectCreationException.class)
                .hasMessage("The project name is empty.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If the path is empty or null, a ProjectCreationException should be thrown.")
    void failCreateProjectSinceEmptyPath(String input) {
        // given
        String projectName = "name";
        String path = input;

        // when

        // then
        assertThatThrownBy(() -> projectService.createProject(projectName, path))
                .isInstanceOf(ProjectCreationException.class)
                .hasMessage("The project path is empty.");
    }
}