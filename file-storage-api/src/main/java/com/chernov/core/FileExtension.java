package com.chernov.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileExtension {

    TXT(".txt"),
    DOC(".doc"),
    DOCX(".docx"),
    PDF(".pdf"),
    XLSX(".xlsx"),
    ZIP(".zip");

    private final String value;
}
