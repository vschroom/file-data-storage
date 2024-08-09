package com.chernov.internal.exceptions;

import com.chernov.FileStorageException;

public class FileUploadMetadataException extends FileStorageException {

    public FileUploadMetadataException(String id, Throwable ex) {
        super(String.format("Error while uploading metadata to file with id=%s", id), ex);
    }
}
