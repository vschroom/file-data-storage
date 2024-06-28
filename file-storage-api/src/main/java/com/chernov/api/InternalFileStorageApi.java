package com.chernov.api;

import com.chernov.core.Attachment;

import java.util.Collection;

public interface InternalFileStorageApi {

    void store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Attachment findBy(String id, Collection<String> metadataKeys);
}