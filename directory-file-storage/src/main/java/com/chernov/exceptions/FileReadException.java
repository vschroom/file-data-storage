package com.chernov.exceptions;

public class FileReadException extends RuntimeException {

    public FileReadException(String path) {
        super("Error while read file: %s".formatted(path));
    }
}