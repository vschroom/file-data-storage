package com.chernov.internal.exceptions;

public class FileUploadMetadataException extends DirectoryFileStorageException {

    public FileUploadMetadataException(String path, Throwable ex) {
        super(String.format("Error while uploading metadata to %s file", path), ex);

    }
}