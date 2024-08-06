package com.chernov;

import java.util.Optional;

public interface FileStorageApi {

    String store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Optional<Attachment> findBy(String id);
}