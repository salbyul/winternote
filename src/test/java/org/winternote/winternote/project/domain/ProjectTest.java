package org.winternote.winternote.project.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.winternote.winternote.project.exception.ProjectCreationException;

import static org.assertj.core.api.Assertions.*;

class ProjectTest {

    @Test
    void buildProject() {
        // given
        String name = "name";
        String path = "path";

        // when
        Project project = new Project(name, path);

        // then
        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getPath()).isEqualTo(path);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If project name is empty, the ProjectCreationException should be thrown.")
    void failProjectBuildSinceEmptyName(String input) {
        // given
        String name = input;
        String path = "path";

        // when

        // then
        assertThatThrownBy(() -> new Project(name, path))
                .isInstanceOf(ProjectCreationException.class)
                .hasMessage("The project name is empty.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    @DisplayName("If project path is empty, the ProjectCreationException should be thrown.")
    void failProjectBuildSinceEmptyPath(String input) {
        // given
        String name = "name";
        String path = input;

        // when

        // then
        assertThatThrownBy(() -> new Project(name, path))
                .isInstanceOf(ProjectCreationException.class)
                .hasMessage("The project path is empty.");
    }

    @Test
    @DisplayName("Test the toString method.")
    void testToString() {
        // given
        String name = "name";
        String path = "path";
        Project project = new Project(name, path);

        // when
        String result = project.toString();

        // then
        assertThat(result).isEqualTo(name + ": " + path);
    }
}