package com.osikov.stas.dataProvider;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;


public class ProductDataProvider implements ArgumentsProvider {

    private static String PRODUCT_NAME = "Rose Gold";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(PRODUCT_NAME));
    }
}
