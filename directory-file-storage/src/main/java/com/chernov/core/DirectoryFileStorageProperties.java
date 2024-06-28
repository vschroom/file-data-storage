package com.chernov.core;

import java.nio.file.Path;

import static java.util.Optional.ofNullable;

public record DirectoryFileStorageProperties(String directories) {

    public Path getDirectories() {
        return ofNullable(this.directories)
                .map(Path::of)
                .orElseGet(() -> Path.of(""));
    }
}
