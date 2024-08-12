package com.chernov.internal.core;

import com.chernov.MinioFileStorageProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class MinioClientConfig {

    private final MinioFileStorageProperties minioFileStorageProperties;

    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioFileStorageProperties.getHost())
                .credentials(
                        minioFileStorageProperties.getUsername(),
                        minioFileStorageProperties.getPassword()
                )
                .build();
    }

    public String getBucket() {
        return ofNullable(minioFileStorageProperties.getBucket())
                .orElseThrow(() -> new IllegalArgumentException("Need to define bucket in your configuration"));
    }
}
