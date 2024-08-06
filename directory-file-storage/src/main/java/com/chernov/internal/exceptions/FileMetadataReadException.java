package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

import static java.lang.String.format;

public class FileMetadataReadException extends FileStorageException {

    public FileMetadataReadException(String path, Throwable ex) {
        super(format("Error while read metadata from path: %s", path), ex);
    }
}