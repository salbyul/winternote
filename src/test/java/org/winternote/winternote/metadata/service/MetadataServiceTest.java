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
import org.winternote.winternote.note.domain.Note;
import org.winternote.winternote.note.domain.NoteSummary;

import java.util.List;

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
    void addRecentNote() {
        // given
        Note note = Note.builder()
                .name("name")
                .location("path")
                .build();

        // when
        when(metadataPersistence.addRecentNote(note)).thenReturn(true);
        doNothing().when(logger).logAddedRecentNotes(note.getName(), note.getLocation());
        metadataService.addRecentNote(note);

        // then
        verify(metadataPersistence, times(1)).addRecentNote(note);
        verify(logger, times(1)).logAddedRecentNotes(note.getName(), note.getLocation());
    }

    @Test
    @DisplayName("If duplicated note add to recent notes list.")
    void addDuplicatedNoteToRecentNote() {
        // given
        Note note = Note.builder()
                .name("name")
                .location("path")
                .build();

        // when
        when(metadataPersistence.addRecentNote(note)).thenReturn(false);
        metadataService.addRecentNote(note);

        // then
        verify(metadataPersistence, times(1)).addRecentNote(note);
        verify(logger, times(0)).logAddedRecentNotes(any(), any());
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

    @Test
    @DisplayName("Return list of NoteSummary")
    void getRecentNoteList() {
        // given
        List<NoteSummary> list = List.of(new NoteSummary("name1", "location1"), new NoteSummary("name2", "location2"));

        // when
        when(metadataPersistence.getRecentNoteList()).thenReturn(list);
        List<NoteSummary> result = metadataService.getRecentNoteList();

        // then
        verify(metadataPersistence, times(1)).getRecentNoteList();
        assertThat(result).isEqualTo(list);
    }
}