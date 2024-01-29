package org.winternote.winternote.model.logging;

public interface WinterLogger {

    /**
     * Logs when a new project created.
     *
     * @param projectName New project name
     * @param path        New project path
     */
    void logNewProject(String projectName, String path);

    /**
     * Logs when a new note created.
     *
     * @param noteName    New note name
     * @param projectName Project name to which the note belongs
     */
    void logNewNote(String noteName, String projectName);

    /**
     * Logs when auto save occurs.
     *
     * @param noteName    Saved note name
     * @param projectName Project name to which the saved note belongs
     */
    void logAutoSave(String noteName, String projectName);

    /**
     * Logs when save occurs.
     *
     * @param noteName    Saved note name
     * @param projectName Project name to which the saved note belongs
     */
    void logSave(String noteName, String projectName);

    /**
     * Logs when a project is deleted.
     *
     * @param projectName Deleted project name
     * @param path        Path of deleted project
     */
    void logDeletedProject(String projectName, String path);

    /**
     * Logs when a note is deleted.
     *
     * @param noteName    Deleted note name
     * @param projectName The name of the project to which the deleted note belongs
     */
    void logDeletedNoteName(String noteName, String projectName);

    /**
     * When an exception occurs, it is logged.
     *
     * @param throwable Exception to be logged
     */
    void logException(Throwable throwable);

    /**
     * Returns the WinterLogger implementation.
     *
     * @return WinterLogger implementation
     */
    static WinterLogger instance() {
        return WinterLoggerImpl.instance();
    }
}
