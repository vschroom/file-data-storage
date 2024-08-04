package com.chernov;

public interface FileStorageApi {

    void store(Attachment attachment);

    boolean exists(String id);

    boolean remove(String id);

    Attachment findBy(String id);
}