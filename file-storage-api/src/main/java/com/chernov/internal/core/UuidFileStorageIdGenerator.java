package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileStorageIdGenerator;
import com.chernov.internal.component.UuidComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UuidFileStorageIdGenerator implements FileStorageIdGenerator {

    private final UuidComponent uuidComponent;

    @Override
    public String generateId(Attachment attachment) {
        return uuidComponent.generateRandom().toString();
    }
}
