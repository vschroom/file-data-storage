package com.chernov;

import com.chernov.internal.core.StorageType;

import java.nio.file.Path;

public class DirectoryStorageConfig {

    public static FileStorageApi fileStorageApi() {
        return FileStorageApiFactory.create(
                DirectoryFileStorageFactory.create(
                        new DirectoryFileStorageProperties(Path.of("custom-dir"), StorageType.ZIP)));
    }
}
