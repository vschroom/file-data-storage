package com.chernov.internal.api;

import com.chernov.Attachment;

import java.util.Collection;

public interface InternalFileStorageApi {

    void store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Attachment findBy(String id, Collection<String> metadataKeys);
}