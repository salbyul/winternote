package org.winternote.winternote.model.logging;

public interface WinterLogger {

    void logNewProject(String projectName, String path);

    void logNewNote(String noteName, String projectName);

    void logAutoSave(String noteName, String projectName);

    void logSave(String noteName, String projectName);

    void logDeletedProject(String projectName, String path);

    void logDeletedNoteName(String noteName, String projectName);

    void logException(Throwable throwable);

    static WinterLogger instance() {
        return WinterLoggerImpl.instance();
    }
}
