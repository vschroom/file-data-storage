package com.chernov;

public class FileStorageApiFactory {

    public static FileStorageApi create(Contract contract) {
        return new FileStorageApiImpl(contract.internalFileStorageApi());
    }
}
