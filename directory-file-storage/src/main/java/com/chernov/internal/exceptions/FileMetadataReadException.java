package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

import static java.lang.String.format;

public class FileMetadataReadException extends FileStorageException {

    public FileMetadataReadException(String id, Throwable ex) {
        super(format("Error while read metadata for attachment with id: %s", id), ex);
    }
}