package com.chernov.internal.exceptions;

import com.chernov.IoFileStorageException;

public class IoFileUploadMetadataException extends IoFileStorageException {

    public IoFileUploadMetadataException(String path, Throwable ex) {
        super(String.format("Error while uploading metadata to %s file", path), ex);
    }
}