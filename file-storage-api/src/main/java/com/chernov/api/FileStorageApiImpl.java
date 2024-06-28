package com.chernov.api;

import com.chernov.core.Attachment;

import java.util.Collection;

public record FileStorageApiImpl(InternalFileStorageApi internalFileStorageApi) implements FileStorageApi {

    @Override
    public void store(Attachment attachment) {
        internalFileStorageApi.store(attachment);
    }

    @Override
    public boolean exists(String id) {
        return internalFileStorageApi.exists(id);
    }

    @Override
    public boolean remove(String id) {
        return internalFileStorageApi.remove(id);
    }

    @Override
    public Attachment findBy(String id, Collection<String> metadataKeys) {
        return internalFileStorageApi.findBy(id, metadataKeys);
    }
}
