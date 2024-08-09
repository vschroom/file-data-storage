package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

import static java.lang.String.format;

public class FileReadException extends FileStorageException {

    public FileReadException(String id, Throwable ex) {
        super(format("Error while read file with id=%s", id), ex);
    }
}