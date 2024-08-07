package com.chernov;

public class CustomGeneratorId implements GeneratorIdService {

    @Override
    public String generateId() {
        return "customId123";
    }
}
