package ru.otus.projs.hw07.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CallWithConvertFromJsonInputService implements CallWithConvertInputService {

    private final ObjectMapper objectMapper;
    private final InOutService inOutService;

    @Override
    public <InType, OutType> OutType callWithConvertInput(Class<InType> inType, String requestMessage, Function<InType, OutType> caller) {
        try {
            inOutService.writeString(requestMessage);
            String inObjectString = inOutService.readString();
            InType inObjectBook = objectMapper.readValue(inObjectString, inType);
            return caller.apply(inObjectBook);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
