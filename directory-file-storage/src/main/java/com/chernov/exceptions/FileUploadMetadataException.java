package com.chernov.exceptions;

public class FileUploadMetadataException extends RuntimeException {

    public FileUploadMetadataException(String path, Throwable ex) {
        super("Error while uploading metadata to %s file".formatted(path), ex);

    }
}