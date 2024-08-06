package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

import static java.lang.String.format;

public class FileReadException extends FileStorageException {

    public FileReadException(String path) {
        super(format("Error while read file: %s", path));
    }
}