package ru.otus.projs.hw02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceMessageWriterService implements MessageWriterService {

    private final InOutService inOutService;
    private final MessageService messageService;

    @Override
    public void writeStringFromSource(String code) {
        inOutService.writeString(messageService.getMessage(code));
    }

    @Override
    public void writeStringFromSource(String code, String ... args) {
        inOutService.writeString(messageService.getMessage(code, args));
    }
}
