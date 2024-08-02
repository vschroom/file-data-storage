package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileCreateDirectoryException extends DirectoryFileStorageException {

    public FileCreateDirectoryException(String directories, Throwable ex) {
        super(format("Error while creating directories: %s", directories), ex);
    }
}
