package com.chernov;

import com.chernov.internal.domain.GeneratorTypeId;

public class MinioStorageConfig {

    public static FileStorageApi fileStorageApi() {
        return FileStorageApiFactory.create(
                MinioFileStorageFactory.create(new MinioFileStorageProperties(
                        "http://localhost:9000",
                        "admin",
                        "12345678",
                        "user-bucket",
                        GeneratorTypeId.CUSTOM_GENERATOR), new CustomFileStorageId()));
    }
}
