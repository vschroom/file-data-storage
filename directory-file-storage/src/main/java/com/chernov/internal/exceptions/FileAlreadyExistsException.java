package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileAlreadyExistsException extends FileStorageException {

    public FileAlreadyExistsException(String path) {
        super(format("File already exists: %s", path));
    }
}
