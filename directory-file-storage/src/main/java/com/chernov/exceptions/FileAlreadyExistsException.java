package com.chernov.exceptions;

public class FileAlreadyExistsException extends RuntimeException {

    public FileAlreadyExistsException(String path) {
        super("File already exists: %s".formatted(path));
    }
}
