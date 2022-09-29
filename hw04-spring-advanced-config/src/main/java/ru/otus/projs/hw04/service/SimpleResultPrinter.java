package ru.otus.projs.hw04.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleResultPrinter implements ResultPrinter {

    private final  InOutService inOutService;

    @Override
    public void print(String result) {
        inOutService.writeString(result);
    }

}
