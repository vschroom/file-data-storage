package com.chernov;

import com.chernov.internal.component.UuidComponent;
import com.chernov.internal.core.DefaultFileStorageIdGenerator;
import com.chernov.internal.core.UuidFileStorageIdGenerator;
import com.chernov.internal.domain.GeneratorTypeId;

public class FileStorageIdGeneratorFactory {

    public static FileStorageIdGenerator create(GeneratorTypeId generatorTypeId, FileStorageIdGenerator customFileStorageIdGenerator) {
        switch (generatorTypeId) {
            case LIB_GENERATOR:
                return new UuidFileStorageIdGenerator(new UuidComponent());
            case CUSTOM_GENERATOR:
                return customFileStorageIdGenerator;
            default:
                return new DefaultFileStorageIdGenerator();
        }
    }
}
