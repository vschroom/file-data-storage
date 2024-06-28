package com.chernov.exceptions;

public class FileCreateDirectoryException extends RuntimeException {

    public FileCreateDirectoryException(String directories, Throwable ex) {
        super("Error while creating directories: %s".formatted(directories), ex);
    }
}
