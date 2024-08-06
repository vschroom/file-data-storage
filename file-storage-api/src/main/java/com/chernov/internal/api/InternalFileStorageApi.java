package com.chernov.internal.api;

import com.chernov.Attachment;

import java.util.Optional;

public interface InternalFileStorageApi {

    String store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Optional<Attachment> findBy(String id);
}