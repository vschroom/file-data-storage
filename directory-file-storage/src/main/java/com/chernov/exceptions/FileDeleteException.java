package com.chernov.exceptions;

public class FileDeleteException extends RuntimeException {

    public FileDeleteException(String path, Throwable ex) {
        super("Error while deleting file: %s".formatted(path), ex);
    }
}