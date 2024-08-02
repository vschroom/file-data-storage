package com.chernov;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryFileStorageProperties {

    private String directories;

    public Path getDirectories() {
        return ofNullable(this.directories)
                .map(Path::of)
                .orElseGet(() -> Path.of(""));
    }
}
