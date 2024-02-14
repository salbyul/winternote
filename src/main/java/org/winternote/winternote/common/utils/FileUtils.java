package org.winternote.winternote.common.utils;

import org.winternote.winternote.common.annotation.Utils;

import java.io.File;

@Utils
public final class FileUtils {

    /**
     * Checks whether a specific file or directory exists in the entered path.
     * Return true, if a file or directory exists.
     *
     * @param path Path that will be checked.
     * @return Whether specific file or directory exists.
     */
    public boolean existsFile(final String path) {
        return new File(path).exists();
    }
}
