package com.chernov;

import com.chernov.internal.api.UuidComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultGeneratorIdService implements GeneratorIdService {

    private final UuidComponent uuidComponent;

    @Override
    public String generateId() {
        return uuidComponent.generateRandom().toString();
    }
}
