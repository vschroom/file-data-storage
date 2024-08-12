package com.chernov;

import com.chernov.internal.core.StorageType;
import com.chernov.internal.domain.GeneratorTypeId;
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

    public Path getDirectory() {
        return ofNullable(this.directory)
                .orElseThrow(() -> new IllegalArgumentException("Directory value cannot be empty or null"));
    }
}
