package org.winternote.winternote.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.metadata.persistence.MetadataPersistence;
import org.winternote.winternote.model.property.PrivateProperty;
import org.winternote.winternote.project.domain.Project;

import java.io.File;

@Service
public class MetadataService {

    private final MetadataPersistence metadataPersistence;
    private final PrivateProperty property;
    private final WinterLogger logger;

    @Autowired
    public MetadataService(final MetadataPersistence metadataPersistence, final PrivateProperty property, final WinterLogger logger) {
        this.metadataPersistence = metadataPersistence;
        this.property = property;
        this.logger = logger;
    }

    public String getRecentLocation() {
        String location = metadataPersistence.getLocation();
        File file = new File(location);
        if (!file.exists()) {
            return property.getApplicationPath();
        }
        return location;
    }

    public void addRecentProject(final Project project) {
        metadataPersistence.addRecentProject(project);
        logger.logAddedRecentProjects(project.toString());
    }

    public void changeLocation(final String newLocation) {
        String oldLocation = getRecentLocation();
        if (!oldLocation.equals(newLocation)) {
            metadataPersistence.changeLocation(newLocation);
            logger.logChangedLocation(oldLocation, newLocation);
        }
    }
}
