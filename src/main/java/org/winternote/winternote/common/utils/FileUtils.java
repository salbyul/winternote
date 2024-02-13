package org.winternote.winternote.common.utils;

import org.winternote.winternote.common.annotation.Utils;

import java.io.File;

@Utils
public class FileUtils {

    public boolean existsFile(final String path) {
        return new File(path).exists();
    }
}
