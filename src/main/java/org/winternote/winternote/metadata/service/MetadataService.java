package org.winternote.winternote.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.winternote.winternote.common.utils.FileUtils;
import org.winternote.winternote.logging.WinterLogger;
import org.winternote.winternote.metadata.persistence.MetadataPersistence;
import org.winternote.winternote.application.property.PrivateProperty;
import org.winternote.winternote.note.domain.Note;

@Service
public class MetadataService {

    private final MetadataPersistence metadataPersistence;
    private final PrivateProperty property;
    private final WinterLogger logger;
    private final FileUtils fileUtils;

    @Autowired
    public MetadataService(final MetadataPersistence metadataPersistence, final PrivateProperty property, final WinterLogger logger, final FileUtils fileUtils) {
        this.metadataPersistence = metadataPersistence;
        this.property = property;
        this.logger = logger;
        this.fileUtils = fileUtils;
    }

    /**
     * Get recent location from the metadata file.
     *
     * @return Recent location.
     */
    public String getRecentLocation() {
        String location = metadataPersistence.getLocation();
        if (!fileUtils.existsFile(location)) {
            return property.getApplicationPath();
        }
        return location;
    }

    /**
     * Add the note to the list of recent notes in the metadata file.
     *
     * @param note Note that will be added.
     */
    public void addRecentNote(final Note note) {
        metadataPersistence.addRecentNote(note);
        logger.logAddedRecentNotes(note.getName(), note.getPath());
    }

    /**
     * Change recent location in the metadata file.
     *
     * @param newLocation New location path.
     */
    public void changeLocation(final String newLocation) {
        String oldLocation = getRecentLocation();
        if (!oldLocation.equals(newLocation)) {
            metadataPersistence.changeLocation(newLocation);
            logger.logChangedLocation(oldLocation, newLocation);
        }
    }
}
