package com.chernov;

import com.chernov.internal.core.FileStorageApiImpl;

public class FileStorageApiFactory {

    public static FileStorageApi create(Contract contract) {
        return new FileStorageApiImpl(contract.internalFileStorageApi());
    }
}
