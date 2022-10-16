package ru.otus.projs.hw07.service;

import java.util.function.Function;

public interface CallWithConvertInputService {

    <InType, OutType> OutType callWithConvertInput(
            Class<InType> inType,
            String requestMessage,
            Function<InType, OutType> caller
    );

}
