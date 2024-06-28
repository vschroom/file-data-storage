package com.chernov.core;

import java.io.InputStream;
import java.util.Map;

public interface Attachment {

    String getId();

    InputStream getContent();

    Map<String, String> getMetadata();

    AttachmentTypeId getTypeId();
}
