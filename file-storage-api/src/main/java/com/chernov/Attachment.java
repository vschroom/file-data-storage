package com.chernov;

import java.io.InputStream;
import java.util.Map;

public interface Attachment {

    String getId();

    InputStream getContent();

    Map<String, String> getMetadata();

    String getFilename();

    FileExtension getFileExtension();

    // FIXME у каждого файла может быть свой сбособ работы с id?
    GeneratorTypeId getGeneratorTypeId();
}
