package com.chernov.internal.api;

import com.chernov.Attachment;

public interface InternalFileStorageApi {

    void store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Attachment findBy(String id);
}