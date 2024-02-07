package org.winternote.winternote.metadata.service;

import org.winternote.winternote.common.service.Service;
import org.winternote.winternote.model.logging.WinterLogger;
import org.winternote.winternote.model.metadata.Metadata;
import org.winternote.winternote.project.domain.Project;

public class MetadataService implements Service {

    private final Metadata metadata;
    private final WinterLogger logger = WinterLogger.instance();

    public MetadataService(final Metadata metadata) {
        this.metadata = metadata;
    }

    public String getRecentLocation() {
        return metadata.getLocation();
    }

    public void addRecentProject(final Project project) {
        metadata.addRecentProject(project);
        logger.logAddedRecentProjects(project.toString());
    }

    public void changeLocation(final String newLocation) {
        String oldLocation = getRecentLocation();
        if (!oldLocation.equals(newLocation)) {
            metadata.changeLocation(newLocation);
            logger.logChangedLocation(oldLocation, newLocation);
        }
    }
}
