package org.winternote.winternote.logging;

public interface WinterLogger {

    /**
     * Logs when a new note created.
     *
     * @param noteName New note name
     * @param path     Where the new note saved.
     */
    void logNewNote(String noteName, String path);

    /**
     * Logs when auto save occurs.
     *
     * @param noteName Saved note name
     * @param path     Where the saved note is.
     */
    void logAutoSave(String noteName, String path);

    /**
     * Logs when save occurs.
     *
     * @param noteName Saved note name
     * @param path     Where the saved note is.
     */
    void logSave(String noteName, String path);

    /**
     * Logs when a note is deleted.
     *
     * @param noteName Deleted note name
     * @param path     Where the deleted note was.
     */
    void logDeletedNote(String noteName, String path);

    /**
     * When an exception occurs, it is logged.
     *
     * @param throwable Exception to be logged
     */
    void logException(Throwable throwable);

    /**
     * Logs when a note are added in metadata recent notes.
     *
     * @param noteName Note name.
     * @param path     Where the note saved.
     */
    void logAddedRecentNotes(String noteName, String path);

    /**
     * Logs when location is changed.
     *
     * @param oldLocation Old location.
     * @param newLocation New location.
     */
    void logChangedLocation(String oldLocation, String newLocation);

    /**
     * Logs when a note is removed from the recent notes list.
     *
     * @param noteName     The note name of the removed note.
     * @param noteLocation The location of the removed note.
     */
    void removeNoteFromRecentNoteList(String noteName, String noteLocation);
}
