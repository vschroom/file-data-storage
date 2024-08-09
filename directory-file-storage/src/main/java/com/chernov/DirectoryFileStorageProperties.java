package com.chernov;

import com.chernov.internal.core.StorageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryFileStorageProperties {

    private Path directory;
    private StorageType storageType;
    private GeneratorTypeId generatorTypeId;
    private GeneratorIdService generatorIdService;

    public Path getDirectory() {
        return ofNullable(this.directory)
                .orElseGet(() -> Path.of("default-dir"));
    }
}
