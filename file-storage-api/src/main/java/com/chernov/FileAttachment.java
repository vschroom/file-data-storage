package com.chernov;

import com.chernov.internal.api.UuidComponent;

import java.io.InputStream;
import java.util.Map;

import static com.chernov.GeneratorTypeId.DEFAULT;
import static java.util.Optional.ofNullable;

public class FileAttachment implements Attachment {

    private final String id;
    private final InputStream content;
    private final Map<String, String> metadata;
    private final String filename;
    private final FileExtension fileExtension;
    private final GeneratorTypeId generatorTypeId;

    public FileAttachment(String id, InputStream content, Map<String, String> metadata, String filename, FileExtension fileExtension) {
        this.id = id;
        this.content = content;
        this.metadata = metadata;
        this.filename = filename;
        this.fileExtension = fileExtension;
        this.generatorTypeId = DEFAULT;
    }

    public FileAttachment(InputStream content, Map<String, String> metadata, String filename, FileExtension fileExtension) {
        this.id = new DefaultGeneratorIdService(new UuidComponent()).generateId();
        this.content = content;
        this.metadata = metadata;
        this.filename = filename;
        this.fileExtension = fileExtension;
        this.generatorTypeId = DEFAULT;
    }

    public FileAttachment(String id,
                          InputStream content,
                          Map<String, String> metadata,
                          String filename,
                          FileExtension fileExtension,
                          GeneratorTypeId generatorTypeId,
                          GeneratorIdService generatorIdService) {
        this.content = content;
        this.metadata = metadata;
        this.filename = filename;
        this.fileExtension = fileExtension;
        this.generatorTypeId = ofNullable(generatorTypeId).orElse(DEFAULT);

        switch (this.generatorTypeId) {
            case CUSTOM_GENERATOR: {
                this.id = generatorIdService.generateId();
                break;
            }
            case CUSTOM: {
                this.id = id;
                break;
            }
            default:
                this.id = new DefaultGeneratorIdService(new UuidComponent()).generateId();
        }
    }

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
    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    @Override
    public GeneratorTypeId getGeneratorTypeId() {
        return this.generatorTypeId;
    }
}
