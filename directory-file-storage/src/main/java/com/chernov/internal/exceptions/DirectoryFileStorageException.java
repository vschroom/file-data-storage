package com.chernov.internal.exceptions;

public class DirectoryFileStorageException extends RuntimeException {

    public DirectoryFileStorageException(String message) {
        super(message);
    }

    public DirectoryFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
