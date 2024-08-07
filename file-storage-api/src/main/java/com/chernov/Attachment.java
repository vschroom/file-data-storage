package com.chernov;

import java.io.InputStream;
import java.util.Map;

public interface Attachment {

    String getId();

    InputStream getContent();

    Map<String, String> getMetadata();

    String getFilename();

    String getFileExtension();
}
