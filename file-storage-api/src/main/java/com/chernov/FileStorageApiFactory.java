package com.chernov;

import com.chernov.internal.impl.FileStorageApiImpl;

public class FileStorageApiFactory {

    public static FileStorageApi create(Contract contract) {
        return new FileStorageApiImpl(contract.internalFileStorageApi());
    }
}
