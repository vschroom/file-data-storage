package com.chernov.internal.domain;

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
    ZIP(".zip"),
    EMPTY_EXT("");

    private final String value;
}
