package org.winternote.winternote.model.application;

import org.winternote.winternote.common.repository.Repository;
import org.winternote.winternote.metadata.service.MetadataService;
import org.winternote.winternote.model.metadata.Metadata;
import org.winternote.winternote.note.repository.NoteRepository;
import org.winternote.winternote.note.service.NoteService;
import org.winternote.winternote.project.repository.ProjectRepository;
import org.winternote.winternote.project.service.ProjectService;
import org.winternote.winternote.common.service.Service;

import java.util.HashMap;
import java.util.Map;

import static org.winternote.winternote.model.property.PrivateProperty.*;

public class ApplicationManager {

    private static final ApplicationManager instance = new ApplicationManager();
    private static final Map<Class<? extends Service>, Service> serviceMap;
    private static final Map<Class<? extends Repository>, Repository> repositoryMap;

    static {
        repositoryMap = new HashMap<>();
        repositoryMap.put(NoteRepository.class, new NoteRepository());
        repositoryMap.put(ProjectRepository.class, new ProjectRepository());

        serviceMap = new HashMap<>();
        serviceMap.put(NoteService.class, new NoteService(getRepository(NoteRepository.class)));
        serviceMap.put(ProjectService.class, new ProjectService(getRepository(ProjectRepository.class)));
        serviceMap.put(MetadataService.class, new MetadataService(new Metadata(APPLICATION_PATH)));

    }

    /**
     * Returns ApplicationManager instance.
     *
     * @return ApplicationManager instance.
     */
    public static ApplicationManager instance() {
        return instance;
    }

    private ApplicationManager() {
        if (instance != null) { // prevent reflect
            throw new UnsupportedOperationException("ApplicationManager has already been initialized. If you want to get instance, you need to invoke instance method.");
        }
    }

    /**
     * Return instance of clazz.
     * <p>
     * If clazz doesn't include this application, IllegalArgumentException will be thrown.
     *
     * @param clazz The class type of the instance to be returned.
     * @return Instance of clazz.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<? extends Service> clazz) {
        boolean containsKey = serviceMap.containsKey(clazz);
        if (containsKey) {
            return (T) serviceMap.get(clazz);
        }
        throw new IllegalArgumentException("Not contains: " + clazz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRepository(Class<? extends Repository> clazz) {
        boolean containsKey = repositoryMap.containsKey(clazz);
        if (containsKey) {
            return (T) repositoryMap.get(clazz);
        }
        throw new IllegalArgumentException("Not contains: " + clazz.getSimpleName());
    }
}