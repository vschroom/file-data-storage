package com.chernov;

import com.chernov.Attachment;
import com.chernov.AttachmentTypeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Map;

@RequiredArgsConstructor
public class FileAttachment implements Attachment {

    private final String id;
    private final InputStream content;
    private final Map<String, String> metadata;
    private final AttachmentTypeId typeId;

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
