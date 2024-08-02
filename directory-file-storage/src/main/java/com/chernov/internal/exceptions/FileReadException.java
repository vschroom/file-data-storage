package com.chernov.internal.exceptions;

import static java.lang.String.format;

public class FileReadException extends DirectoryFileStorageException {

    public FileReadException(String path) {
        super(format("Error while read file: %s", path));
    }
}