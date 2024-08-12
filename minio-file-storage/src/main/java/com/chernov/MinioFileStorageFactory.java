package com.chernov;

import com.chernov.internal.core.MinioClientConfig;
import com.chernov.internal.core.MinioFileStorageApi;
import com.chernov.internal.core.MinioFileStorageServiceImpl;

public class MinioFileStorageFactory {

    public static Contract create(MinioFileStorageProperties minioFileStorageProperties, FileStorageIdGenerator customFileStorageIdGenerator) {
        var minioClientConfig = new MinioClientConfig(minioFileStorageProperties);
        var minioFileStorageService = new MinioFileStorageServiceImpl(minioClientConfig.minioClient(), minioClientConfig.getBucket());
        minioFileStorageService.makeBucketIfNotExists();

        var fileStorageIdGenerator = FileStorageIdGeneratorFactory.create(
                minioFileStorageProperties.getGeneratorTypeId(), customFileStorageIdGenerator);

        return () -> new MinioFileStorageApi(minioFileStorageService, fileStorageIdGenerator);
    }
}
