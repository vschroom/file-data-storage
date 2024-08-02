package com.chernov;

import com.chernov.internal.api.InternalFileStorageApi;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
class FileStorageApiImpl implements FileStorageApi {

    private final InternalFileStorageApi internalFileStorageApi;

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
