package com.chernov.internal.exceptions;

// FIXME где корневой Exception?
public class DirectoryFileStorageException extends RuntimeException {

    public DirectoryFileStorageException(String message) {
        super(message);
    }

    public DirectoryFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
