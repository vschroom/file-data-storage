package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileCreateException extends FileStorageException {

    public FileCreateException(String directories, Throwable ex) {
        super(format("Error while creating directories: %s", directories), ex);
    }
}
