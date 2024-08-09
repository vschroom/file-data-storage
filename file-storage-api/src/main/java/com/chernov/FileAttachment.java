package com.chernov;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Map;

@RequiredArgsConstructor
public class FileAttachment implements Attachment {

    private final String id;
    private final InputStream content;
    private final Map<String, String> metadata;
    private final String filename;
    private final String fileExtension;

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
    public String getFilename() {
        return this.filename;
    }

    @Override
    public String getFileExtension() {
        return this.fileExtension;
    }
}
