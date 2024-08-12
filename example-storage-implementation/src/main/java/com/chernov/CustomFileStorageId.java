package com.chernov;

public class CustomFileStorageId implements FileStorageIdGenerator {

    @Override
    public String generateId(Attachment attachment) {
        return "customId" + attachment.getId();
    }
}
