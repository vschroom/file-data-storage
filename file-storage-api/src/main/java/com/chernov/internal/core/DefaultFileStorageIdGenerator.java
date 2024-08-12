package com.chernov.internal.core;

import com.chernov.Attachment;
import com.chernov.FileStorageIdGenerator;

public class DefaultFileStorageIdGenerator implements FileStorageIdGenerator {

    @Override
    public String generateId(Attachment attachment) {
        return attachment.getId();
    }
}
