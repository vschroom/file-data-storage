package com.chernov;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FileExtension {
    TXT(".txt"),
    DOC(".doc"),
    DOCX(".docx"),
    PDF(".pdf"),
    XLSX(".xlsx"),
    ZIP(".zip"),
    EMPTY_EXT("");

    private final String value;

    public static FileExtension parse(String extension) {
        return Stream.of(FileExtension.values())
                .filter(fe -> fe.getValue().equals(extension))
                .findFirst()
                .orElse(EMPTY_EXT);
    }
}
