package org.winternote.winternote.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.winternote.winternote.common.utils.FileUtils;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.metadata.persistence.MetadataPersistence;
import org.winternote.winternote.model.property.PrivateProperty;
import org.winternote.winternote.project.domain.Project;

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

    public String getRecentLocation() {
        String location = metadataPersistence.getLocation();
        if (!fileUtils.existsFile(location)) {
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
