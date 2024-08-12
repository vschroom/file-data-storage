package com.chernov;

import com.chernov.internal.core.StorageType;
import com.chernov.internal.domain.GeneratorTypeId;

import java.nio.file.Path;

public class DirectoryStorageConfig {

    public static FileStorageApi fileStorageApi() {
        return FileStorageApiFactory.create(
                DirectoryFileStorageFactory.create(
                        new DirectoryFileStorageProperties(
                                Path.of("user-dir"),
                                StorageType.ZIP,
                                GeneratorTypeId.CUSTOM_GENERATOR), new CustomFileStorageId()));
    }
}
