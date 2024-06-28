package com.chernov.exceptions;

public class FileMetadataReadException extends RuntimeException {

    public FileMetadataReadException(String path, Throwable ex) {
        super("Error while read metadata from path: %s".formatted(path), ex);
    }
}