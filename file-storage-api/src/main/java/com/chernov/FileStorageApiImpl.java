package com.chernov;

import com.chernov.internal.api.InternalFileStorageApi;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class FileStorageApiImpl implements FileStorageApi {

    private final InternalFileStorageApi internalFileStorageApi;

    @Override
    public String store(Attachment attachment) {
        return internalFileStorageApi.store(attachment);
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
    public Optional<Attachment> findBy(String id) {
        return internalFileStorageApi.findBy(id);
    }
}
