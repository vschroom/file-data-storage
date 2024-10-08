package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileDeleteException extends FileStorageException {

    public FileDeleteException(String path, Throwable ex) {
        super(format("Error while deleting file: %s", path), ex);
    }
}