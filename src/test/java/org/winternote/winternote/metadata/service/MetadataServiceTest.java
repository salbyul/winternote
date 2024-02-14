package org.winternote.winternote.metadata.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.winternote.winternote.common.utils.FileUtils;
import org.winternote.winternote.metadata.persistence.MetadataPersistence;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.project.domain.Project;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest {

    @Mock
    MetadataPersistence metadataPersistence;

    @Mock
    PrivateProperty property;

    @Mock
    WinterLogger logger;

    @Mock
    FileUtils fileUtils;

    @InjectMocks
    MetadataService metadataService;

    @Test
    @DisplayName("Can get recent location")
    void getRecentLocation() {
        // given
        String location = "location";

        // when
        when(metadataPersistence.getLocation()).thenReturn(location);
        when(fileUtils.existsFile(location)).thenReturn(true);
        String recentLocation = metadataService.getRecentLocation();

        // then
        assertThat(recentLocation).isEqualTo("location");
        verify(metadataPersistence, times(1)).getLocation();
        verify(fileUtils, times(1)).existsFile(location);
        verify(property, times(0)).getApplicationPath();
    }

    @Test
    @DisplayName("Returns the application path if the recent location is polluted")
    void getRecentLocationReturnApplicationPathIfInvalidRecentLocation() {
        // given
        String location = "location";
        String applicationPath = "applicationPath";

        // when
        when(metadataPersistence.getLocation()).thenReturn(location);
        when(fileUtils.existsFile(location)).thenReturn(false);
        when(property.getApplicationPath()).thenReturn(applicationPath);
        String recentLocation = metadataService.getRecentLocation();

        // then
        assertThat(recentLocation).isEqualTo(applicationPath);
        verify(metadataPersistence, times(1)).getLocation();
        verify(fileUtils, times(1)).existsFile(location);
        verify(property, times(1)).getApplicationPath();
    }

    @Test
    @DisplayName("Can add recent project.")
    void addRecentProject() {
        // given
        Project project = Project.builder()
                .name("name")
                .path("path")
                .build();

        // when
        doNothing().when(metadataPersistence).addRecentProject(project);
        doNothing().when(logger).logAddedRecentProjects(project.toString());
        metadataService.addRecentProject(project);

        // then
        verify(metadataPersistence, times(1)).addRecentProject(project);
        verify(logger, times(1)).logAddedRecentProjects(project.toString());
    }

    @Test
    @DisplayName("Can change Location")
    void changeLocation() {
        // given
        String oldLocation = "oldLocation";
        String newLocation = "newLocation";

        // when
        when(metadataPersistence.getLocation()).thenReturn(oldLocation);
        when(fileUtils.existsFile(oldLocation)).thenReturn(true);
        doNothing().when(metadataPersistence).changeLocation(newLocation);
        doNothing().when(logger).logChangedLocation(oldLocation, newLocation);
        metadataService.changeLocation(newLocation);

        // then
        verify(metadataPersistence, times(1)).changeLocation(newLocation);
        verify(logger, times(1)).logChangedLocation(oldLocation, newLocation);
    }

    @Test
    @DisplayName("If newLocation is equal to oldLocation, ChangeLocation do nothing")
    void changeLocationIgnoredWhenSameLocation() {
        // given
        String oldLocation = "oldLocation";
        String newLocation = "oldLocation";

        // when
        when(metadataPersistence.getLocation()).thenReturn(oldLocation);
        when(fileUtils.existsFile(oldLocation)).thenReturn(true);
        metadataService.changeLocation(newLocation);

        // then
        verify(metadataPersistence, times(0)).changeLocation(newLocation);
        verify(logger, times(0)).logChangedLocation(oldLocation, newLocation);
    }
}