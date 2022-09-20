package ru.otus.projs.hw02.service;

public interface MessageWriterService {

    void writeStringFromSource(String code);
    void writeStringFromSource(String code, String ... args);

}
