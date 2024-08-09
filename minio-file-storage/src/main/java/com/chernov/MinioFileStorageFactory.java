package com.chernov;

import com.chernov.internal.core.MinioClientConfig;
import com.chernov.internal.core.MinioFileStorageApi;
import com.chernov.internal.core.MinioFileStorageServiceImpl;

public class MinioFileStorageFactory {

    public static Contract create(MinioFileStorageProperties minioFileStorageProperties) {
        var minioClientConfig = new MinioClientConfig(minioFileStorageProperties);
        var minioFileStorageService = new MinioFileStorageServiceImpl(minioClientConfig.minioClient(), minioClientConfig.getBucket());

        return () -> new MinioFileStorageApi(
                minioFileStorageService,
                minioFileStorageProperties.getGeneratorTypeId(),
                minioFileStorageProperties.getGeneratorIdService());
    }
}
