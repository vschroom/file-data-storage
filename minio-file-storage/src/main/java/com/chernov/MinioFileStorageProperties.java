package com.chernov;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MinioFileStorageProperties {

    private final String host;
    private final String username;
    private final String password;
    private final String bucket;
    private final GeneratorTypeId generatorTypeId;
    private final GeneratorIdService generatorIdService;
}
