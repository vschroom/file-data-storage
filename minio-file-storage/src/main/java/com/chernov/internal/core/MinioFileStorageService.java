package com.chernov.internal.core;

import com.chernov.Attachment;

import java.io.InputStream;

interface MinioFileStorageService {

    void putObject(Attachment attachment, String bucket);

    boolean bucketExists(String bucket);

    void makeBucketIfNotExists(String bucket);

    void removeObject(String bucket);

    InputStream getContent(String bucket);
}
