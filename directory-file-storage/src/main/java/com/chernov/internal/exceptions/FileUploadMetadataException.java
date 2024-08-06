package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

public class FileUploadMetadataException extends FileStorageException {

    public FileUploadMetadataException(String path, Throwable ex) {
        super(String.format("Error while uploading metadata to %s file", path), ex);

    }
}