package com.chernov.core;

import java.io.InputStream;
import java.util.Map;

public record FileAttachment(String id, InputStream content, Map<String, String> metadata, AttachmentTypeId typeId)
        implements Attachment {

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public InputStream getContent() {
        return this.content;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    @Override
    public AttachmentTypeId getTypeId() {
        return this.typeId;
    }
}
