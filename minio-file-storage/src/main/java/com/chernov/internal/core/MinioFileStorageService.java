package com.chernov.internal.core;

import com.chernov.Attachment;

import java.io.InputStream;
import java.util.Map;

interface MinioFileStorageService {

    void putObject(Attachment attachment);

    boolean hasObject(String id);

    void removeObject(String id);

    InputStream getContent(String id);

    Map<String, String> getUserMetadata(String id);
}
