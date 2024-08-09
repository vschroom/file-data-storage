package com.chernov;

public class MinioStorageConfig {

    public static FileStorageApi fileStorageApi() {
        return FileStorageApiFactory.create(
                MinioFileStorageFactory.create(new MinioFileStorageProperties(
                        "http://localhost:9000",
                        "admin",
                        "12345678",
                        null,
                        GeneratorTypeId.CUSTOM_GENERATOR,
                        new CustomGeneratorId())));
    }
}
